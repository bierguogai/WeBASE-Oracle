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

package com.webank.oracle.transaction;

import com.webank.oracle.base.exception.OracleException;
import com.webank.oracle.base.properties.EventRegister;
import com.webank.oracle.base.properties.EventRegisterProperties;
import com.webank.oracle.base.pojo.vo.ConstantCode;
import com.webank.oracle.base.utils.DecodeOutputUtils;
import com.webank.oracle.http.HttpService;
import com.webank.oracle.keystore.KeyStoreService;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.fisco.bcos.web3j.utils.Numeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static com.webank.oracle.base.utils.JsonUtils.toJSONString;

/**
 * OracleService.
 */
@Slf4j
@Service
public class OracleService {
    @Autowired
    Map<Integer,Map<Integer, Web3j>> web3jMap;
    @Autowired
    private EventRegisterProperties properties;
    @Autowired
    private KeyStoreService keyStoreService;
    @Autowired
    private HttpService httpService;
    private BigInteger gasPrice = new BigInteger("1");
    private BigInteger gasLimit = new BigInteger("2100000000");
    private ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);


    /**
     * Get data from url in contractEventLog and upload it to blockChain.
     */
    public void getDataFromUrlAndUpChain(String contractAddress, byte[] logId, String url,String formate, List<String> httpResultIndexList, int chainId, int groupId) throws Exception {
        //get data
        Object httpResult = httpService.getObjectByUrlAndKeys(url,formate, httpResultIndexList);
        log.info("url {} https result: {} ", url, toJSONString(httpResult));
        //send transaction
        upBlockChain(contractAddress, logId, toJSONString(httpResult), chainId, groupId);
    }


    /**
     * 将数据上链.
     */
    public void upBlockChain(String contractAddress, byte[] cid, String data, int chainId, int groupId) throws Exception {
        String cidStr = Numeric.toHexString(cid);
        log.debug("upBlockChain start. contractAddress:{} data:{} cid:{}", contractAddress, data, cidStr);
        try {
            Web3j web3j = getWeb3j(chainId,groupId);
            Credentials credentials = keyStoreService.getCredentials();
            TemplateOracle templateOracle = TemplateOracle.load(contractAddress, web3j, credentials, contractGasProvider);
            TransactionReceipt receipt = templateOracle.__callback(cid, data).send();
            log.info("&&&&&" + receipt.getStatus());
            dealWithReceipt(receipt);
            log.info("upBlockChain success chainId: {}  groupId: {} . contractAddress:{} data:{} cid:{}",chainId,groupId, contractAddress, data, cidStr);
        } catch (OracleException oe) {
            //todo
            log.error("upBlockChain exception chainId: {}  groupId: {} . contractAddress:{} data:{} cid:{}", chainId, groupId, contractAddress, data, cidStr, oe);
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
            log.error("transaction error", DecodeOutputUtils.decodeOutputReturnString0x16(receipt.getOutput()));
            throw new OracleException(ConstantCode.SYSTEM_EXCEPTION.getCode(), DecodeOutputUtils.decodeOutputReturnString0x16(receipt.getOutput()));
        }
        if (!"0x0".equals(receipt.getStatus())) {
            log.error("transaction error, status:{} output:{}", receipt.getStatus(), receipt.getOutput());
            throw new OracleException(ConstantCode.SYSTEM_EXCEPTION.getCode(), DecodeOutputUtils.decodeOutputReturnString0x16(receipt.getOutput()));
        }
    }

    public   String deployOracleCore(int chainId, int group)  {
        Credentials credentials = keyStoreService.getCredentials();
        OracleCore oraliceCore = null;
        try {
            oraliceCore = OracleCore.deploy(getWeb3j(chainId, group), credentials, contractGasProvider).send();
        }
        catch (OracleException e ){
            throw e ;
        }
        catch  (Exception e) {
            throw new OracleException(ConstantCode.DEPLOY_FAILED);
        }
        String orcleAddress = oraliceCore.getContractAddress();
        return orcleAddress;
    }
}
