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

package com.webank.oracle.event.service.vrf;

import static com.webank.oracle.base.pojo.vo.ConstantCode.VRF_CONTRACT_ADDRESS_ERROR;

import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.utils.Numeric;
import org.springframework.stereotype.Service;

import com.webank.oracle.base.enums.ContractTypeEnum;
import com.webank.oracle.base.exception.OracleException;
import com.webank.oracle.base.pojo.vo.ConstantCode;
import com.webank.oracle.event.service.AbstractCoreService;
import com.webank.oracle.event.vo.BaseLogResult;
import com.webank.oracle.event.vo.vrf.VRFLogResult;
import com.webank.oracle.transaction.vrf.LibVRF;
import com.webank.oracle.transaction.vrf.VRFCoordinator;

import lombok.extern.slf4j.Slf4j;

/**
 * VRFService.
 */
@Slf4j
@Service
public class VRFService extends AbstractCoreService {

    @Override
    public ContractTypeEnum getContractType() {
        return ContractTypeEnum.VRF;
    }

    /**
     * @param chainId
     * @param groupId
     * @return
     */
    @Override
    protected String deployContract(int chainId, int groupId) {
        Credentials credentials = keyStoreService.getCredentials();
        VRFCoordinator vrfCoordinator = null;
        try {
            vrfCoordinator = VRFCoordinator.deploy(getWeb3j(chainId, groupId), credentials, contractGasProvider).send();
        } catch (OracleException e) {
            throw e;
        } catch (Exception e) {
            throw new OracleException(ConstantCode.DEPLOY_FAILED);
        }
        return vrfCoordinator.getContractAddress();
    }

    @Override
    public String getResultAndUpToChain(int chainId, int groupId, BaseLogResult baseLogResult) throws Exception {
        VRFLogResult vrfLogResult = (VRFLogResult) baseLogResult;

        String requestId = vrfLogResult.getRequestId();
        String keyHash = vrfLogResult.getKeyHash();
        BigInteger seed = vrfLogResult.getSeed();
        BigInteger blockNumber = vrfLogResult.getBlockNumber();
        String sender = vrfLogResult.getSender();
        String seedAndBlockNum = vrfLogResult.getSeedAndBlockNum();

        Credentials credentials = keyStoreService.getCredentials();
        String servicePrivateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);

        log.info("Call vrf lib:[{}].", requestId);
        String proof = LibVRF.InstanceHolder.getInstance().VRFProoFGenerate(servicePrivateKey, seed.toString(16));
        log.info("Generate proof:[{}] for request:[{}]", proof, requestId);

        this.fulfill(chainId, groupId, sender, baseLogResult, proof);
        return proof;
    }

    /**
     * 将数据上链.
     */
    @Override
    public void fulfill(int chainId, int groupId, String contractAddress, BaseLogResult baseLogResult, Object result) throws Exception {
        // TODO.
        VRFLogResult vrfLogResult = (VRFLogResult) baseLogResult;

        String proof = String.valueOf(result);
        String requestId = vrfLogResult.getRequestId();
        BigInteger blockNumber = vrfLogResult.getBlockNumber();

        String sender = vrfLogResult.getSender();
        log.debug("upBlockChain start. contractAddress:{} data:{} requsetId:{}", sender, proof, requestId);
        try {
            Web3j web3j = getWeb3j(chainId, groupId);
            Credentials credentials = keyStoreService.getCredentials();

            String vrfCoordinatorAddress = contractAddressMap.get(getKey(chainId, groupId));
            if (StringUtils.isBlank(vrfCoordinatorAddress)) {
                throw new OracleException(VRF_CONTRACT_ADDRESS_ERROR);
            }

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
     * TODO. Use go lib or java implementation
     *
     * @param chainId
     * @param group
     * @param proof
     * @return
     */
    public Pair<String, String> decodeProof(int chainId, int group, String proof) throws Exception {
//        Pair<String, String> addressMap = VRF_CONTRACT_ADDRESS_MAP.get(getKey(chainId, group));
//        if (addressMap == null){
//            // TODO. throw exception
//            return Pair.of("","");
//        }
//
//        Credentials credentials = keyStoreService.getCredentials();
//        //fist  secretRegistty
//        VRF vrf = VRF.load(addressMap.getValue(), getWeb3j(chainId, group), credentials, contractGasProvider);
//
//        byte[] proofBytes = Numeric.hexStringToByteArray(proof);
//
//        TransactionReceipt tt = vrf.randomValueFromVRFProof(proofBytes).send();
//
//        return Pair.of(tt.getStatus(), tt.getOutput());
        return Pair.of("0", proof);
    }
}

