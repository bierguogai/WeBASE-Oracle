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

package com.webank.oracle.transaction.oraclize;

import static com.webank.oracle.base.utils.JsonUtils.toJSONString;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.utils.Numeric;
import org.springframework.stereotype.Service;

import com.webank.oracle.base.exception.OracleException;
import com.webank.oracle.base.pojo.vo.ConstantCode;
import com.webank.oracle.base.utils.JsonUtils;
import com.webank.oracle.event.service.AbstractCoreService;
import com.webank.oracle.event.vo.BaseLogResult;
import com.webank.oracle.event.vo.oraclize.OraclizeLogResult;

import lombok.extern.slf4j.Slf4j;

/**
 * OracleService.
 */
@Slf4j
@Service
public class OracleService extends AbstractCoreService {

    private final static Map<String, String> ORACLIZE_CONTRACT_ADDRESS_MAP = new ConcurrentHashMap<>();

    @Override
    public String deployContract(int chainId, int group) {
        Credentials credentials = keyStoreService.getCredentials();
        OracleCore oraliceCore = null;
        try {
            oraliceCore = OracleCore.deploy(getWeb3j(chainId, group), credentials, contractGasProvider).send();
        } catch (OracleException e) {
            throw e;
        } catch (Exception e) {
            throw new OracleException(ConstantCode.DEPLOY_FAILED);
        }
        ORACLIZE_CONTRACT_ADDRESS_MAP.put(getKey(chainId, group), oraliceCore.getContractAddress());
        return oraliceCore.getContractAddress();
    }

    @Override
    public String getResult(int chainId, int groupId, BaseLogResult baseLogResult) throws Exception {
        // TODO. convert check ?
        OraclizeLogResult oraclizeLogResult = (OraclizeLogResult) baseLogResult;

        // TODO. optimize
        String url = oraclizeLogResult.getUrl();
        if (url.startsWith("\"")) {
            int len1 = url.length();
            url = url.substring(1, len1 - 1);
        }
        int left = url.indexOf("(");
        int right = url.indexOf(")");
        String format = url.substring(0, left);
        url = url.substring(left + 1, right);
        List<String> httpResultIndexList = subFiledValueForHttpResultIndex(url);

        //get data
        BigInteger httpResult = httpService.getObjectByUrlAndKeys(url,
                format, httpResultIndexList);
        log.info("url {} https result: {} ", oraclizeLogResult.getUrl(), toJSONString(httpResult));

        this.fill(chainId, groupId, oraclizeLogResult.getCallbackAddress(), oraclizeLogResult, httpResult);
        return toJSONString(httpResult);
    }

    /**
     * 将数据上链.
     */
    @Override
    public void fill(int chainId, int groupId, String contractAddress, BaseLogResult baseLogResult, Object result) throws Exception {
        //send transaction
        OraclizeLogResult oraclizeLogResult = (OraclizeLogResult) baseLogResult;
        String requestId = oraclizeLogResult.getRequestId();
        log.info("Start to write data to chain, contractAddress:{} data:{}", JsonUtils.toJSONString(baseLogResult), result);

        BigInteger afterTimesAmount = ((BigInteger) result).multiply(oraclizeLogResult.getTimesAmount());
        log.info("After times amount:[{}]", Hex.encodeHexString(afterTimesAmount.toByteArray()));
        try {

            Web3j web3j = getWeb3j(chainId, groupId);
            Credentials credentials = keyStoreService.getCredentials();
            String oraclizeAddress = ORACLIZE_CONTRACT_ADDRESS_MAP.get(getKey(chainId, groupId));
            if (StringUtils.isBlank(oraclizeAddress)) {
                // TODO. throw exception
            }
            OracleCore templateOracle = OracleCore.load(oraclizeAddress, web3j, credentials, contractGasProvider);
            TransactionReceipt receipt = templateOracle.fulfillRequest(Numeric.hexStringToByteArray(requestId),
                    // TODO. safe convert ????? biginteger
                    oraclizeLogResult.getCallbackAddress(), oraclizeLogResult.getExpiration(), afterTimesAmount).send();
            log.info("Write data to chain:[{}]", receipt.getStatus());
            dealWithReceipt(receipt);
            log.info("upBlockChain success chainId: {}  groupId: {} . contractAddress:{} data:{} requestId:{}", chainId, groupId, contractAddress, afterTimesAmount, requestId);
        } catch (Exception oe) {
            log.error("upBlockChain exception chainId: {}  groupId: {} . contractAddress:{} data:{} requestId:{}", chainId, groupId, contractAddress, afterTimesAmount, requestId, oe);
            throw oe;
        }
    }

    /**
     * @param argValue
     * @return
     */
    private String subFiledValueForUrl(String argValue) {
        if (StringUtils.isBlank(argValue)) {
            log.warn("argValue is empty");
            return argValue;
        }
        int left = argValue.indexOf("(");
        int right = argValue.indexOf(")");
        String header = argValue.substring(0, left);
        String url = argValue.substring(left, right);
        return url;
    }


    /**
     * @param argValue
     * @return
     */
    private List<String> subFiledValueForHttpResultIndex(String argValue) {
        if (StringUtils.isBlank(argValue) || argValue.endsWith(")")) {
            log.warn("argValue is:{} ,return empty list", argValue);
            return Collections.EMPTY_LIST;
        }

        String resultIndex = argValue.substring(argValue.indexOf(").") + 2);

        String[] resultIndexArr = resultIndex.split("\\.");//.replaceAll("\\.", ",").split(",")
        List resultList = new ArrayList<>(resultIndexArr.length);
        Collections.addAll(resultList, resultIndexArr);
        return resultList;
    }

}
