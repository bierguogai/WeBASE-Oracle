/*
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.webank.oracle.transaction.vrf;

import java.math.BigInteger;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.fisco.bcos.web3j.utils.Numeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webank.oracle.base.exception.OracleException;
import com.webank.oracle.base.pojo.vo.ConstantCode;
import com.webank.oracle.base.utils.DecodeOutputUtils;
import com.webank.oracle.keystore.KeyStoreService;

import lombok.extern.slf4j.Slf4j;

/**
 * VRFService.
 */
@Slf4j
@Service
public class VRFService {

    @Autowired
    Map<Integer, Map<Integer, Web3j>> web3jMap;
    @Autowired
    private KeyStoreService keyStoreService;
    private BigInteger gasPrice = new BigInteger("1");
    private BigInteger gasLimit = new BigInteger("2100000000");
    private ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);

    private static String vrfCoordinatorAddress;
    private static String vrfAddress;

    /**
     * @param requestID
     * @param keyHash
     * @param seed
     * @param blockNumber
     * @param sender
     * @param seedAndBlockNum
     * @param chainId
     * @param groupId
     * @return
     * @throws Exception
     */
    public String generateAndUpChain(String requestID, String keyHash, BigInteger seed, BigInteger blockNumber, String sender, String seedAndBlockNum, int chainId, int groupId) throws Exception {
        Credentials credentials = keyStoreService.getCredentials();
        String servicePrivateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);

        String proof = LibVRF.InstanceHolder.getInstance().VRFProoFGenerate(servicePrivateKey, seed.toString(16));
        log.info("Generate proof:[{}] for request:[{}]", proof, requestID);

        upBlockChain(sender, requestID, proof, blockNumber, chainId, groupId);
        return proof;
    }


    /**
     * 将数据上链.
     */
    public void upBlockChain(String sender, String requestId, String proof, BigInteger blockNumber, int chainId, int groupId) throws Exception {
        log.debug("upBlockChain start. contractAddress:{} data:{} requsetId:{}", sender, proof, requestId);
        try {
            Web3j web3j = getWeb3j(chainId, groupId);
            Credentials credentials = keyStoreService.getCredentials();
            VRFCoordinator vrfCoordinator = VRFCoordinator.load(vrfCoordinatorAddress, web3j, credentials, contractGasProvider);

            byte[] bnbytes = Numeric.toBytesPadded(blockNumber, 32);
            byte[] i = Numeric.hexStringToByteArray(proof);
            byte[] destination = new byte[i.length + 32];
            System.arraycopy(i, 0, destination, 0, i.length);
            System.arraycopy(bnbytes, 0, destination, i.length, 32);

            TransactionReceipt receipt = vrfCoordinator.fulfillRandomnessRequest(destination).send();
            log.info("&&&&&" + receipt.getStatus());
            dealWithReceipt(receipt);
            log.info("upBlockChain success chainId: {}  groupId: {} . contractAddress:{} data:{} cid:{}", chainId, groupId, sender, proof, requestId);
        } catch (OracleException oe) {
            log.error("upBlockChain exception chainId: {}  groupId: {} . contractAddress:{} data:{} cid:{}", chainId, groupId, sender, proof, requestId, oe);
            throw oe;
        }
    }


    /**
     * get web3j by groupId.
     */
    private Web3j getWeb3j(int chainId, int groupId) {
        Web3j web3j = web3jMap.get(chainId).get(groupId);
        if (web3j == null) {
            new OracleException(ConstantCode.GROUP_ID_NOT_EXIST);
        }
        return web3j;
    }

    /**
     * @param receipt
     */
    public static void dealWithReceipt(TransactionReceipt receipt) {
        // log.info("*********"+ transactionReceipt.getOutput());
        if ("0x16".equals(receipt.getStatus()) && receipt.getOutput().startsWith("0x08c379a0")) {
            log.error("transaction error:[{}]", DecodeOutputUtils.decodeOutputReturnString0x16(receipt.getOutput()));
            throw new OracleException(ConstantCode.SYSTEM_EXCEPTION.getCode(), DecodeOutputUtils.decodeOutputReturnString0x16(receipt.getOutput()));
        }
        if (!"0x0".equals(receipt.getStatus())) {
            log.error("transaction error, status:{} output:{}", receipt.getStatus(), receipt.getOutput());
            throw new OracleException(ConstantCode.SYSTEM_EXCEPTION.getCode(), DecodeOutputUtils.decodeOutputReturnString0x16(receipt.getOutput()));
        }
    }

    public String deployVRF(int chainId, int group) {
        Credentials credentials = keyStoreService.getCredentials();
        VRFCoordinator vrfCoordinator = null;
        VRF vrf = null;
        try {
            vrfCoordinator = VRFCoordinator.deploy(getWeb3j(chainId, group), credentials, contractGasProvider).send();
            vrf  = VRF.deploy(getWeb3j(chainId,group),credentials,contractGasProvider).send();
        } catch (OracleException e) {
            throw e;
        } catch (Exception e) {
            throw new OracleException(ConstantCode.DEPLOY_FAILED);
        }
        vrfCoordinatorAddress = vrfCoordinator.getContractAddress();
        this.vrfAddress = vrf.getContractAddress();
        return  vrfCoordinator.getContractAddress();
    }

    /**
     *  TODO. check tt.getStatus()
     *
     * @param chainId
     * @param group
     * @param proof
     * @return
     */
    public Pair<String, String> decodeProof(int chainId, int group, String proof) throws Exception {
        Credentials credentials = keyStoreService.getCredentials();
        //fist  secretRegistty
        VRF vrf = VRF.load(vrfAddress,getWeb3j(chainId, group),credentials,contractGasProvider);

        byte[] proofBytes = Numeric.hexStringToByteArray(proof);

        TransactionReceipt tt = vrf.randomValueFromVRFProof(proofBytes).send();

        return Pair.of(tt.getStatus(),tt.getOutput());
    }
}
