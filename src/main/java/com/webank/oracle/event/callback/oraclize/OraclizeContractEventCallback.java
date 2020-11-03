/**
 * Copyright 2014-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webank.oracle.event.callback.oraclize;

import static com.webank.oracle.base.properties.ConstantProperties.MAX_ERROR_LENGTH;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.tx.txdecode.BaseException;
import org.fisco.bcos.web3j.tx.txdecode.LogResult;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;
import org.fisco.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webank.oracle.base.enums.OracleVersionEnum;
import com.webank.oracle.base.enums.ReqStatusEnum;
import com.webank.oracle.base.enums.SourceTypeEnum;
import com.webank.oracle.base.exception.OracleException;
import com.webank.oracle.base.exception.RemoteCallException;
import com.webank.oracle.base.utils.CommonUtils;
import com.webank.oracle.base.utils.ThreadLocalHolder;
import com.webank.oracle.repository.ReqHistoryRepository;
import com.webank.oracle.repository.domian.ReqHistory;
import com.webank.oracle.transaction.oraclize.OracleService;

/**
 * 从callback中获取事件推送过来的请求地址，再请求该地址获取数据上链。
 */
public class OraclizeContractEventCallback extends EventLogPushWithDecodeCallback {

    private static final String LOG_ID = "cid";
    private static final String LOG_ARG = "arg";
    private static final String LOG_SENDER = "sender";
    private static final String LOG_TIMESTAMP = "timestamp";
    private static final String LOG_DATASOURCE = "datasource";

    private static final Logger logger =
            LoggerFactory.getLogger(OraclizeContractEventCallback.class);

    private OracleService oracleService;
    private ReqHistoryRepository reqHistoryRepository;

    private int chainId;
    private int groupId;


    public OraclizeContractEventCallback(OracleService oracleService, ReqHistoryRepository reqHistoryRepository,
                                         TransactionDecoder decoder, int chainId, int groupId) {
        // onPush will call father class's decoder, init EventLogPushWithDecodeCallback's decoder
        this.setDecoder(decoder);
        this.oracleService = oracleService;
        this.chainId = chainId;
        this.groupId = groupId;
        this.reqHistoryRepository = reqHistoryRepository;
    }


    /**
     * 根据Log对象中的blockNumber，transactionIndex，logIndex进行去重
     *
     * @param status
     * @param logs
     */
    @Override
    public void onPushEventLog(int status, List<LogResult> logs) {
        long start = ThreadLocalHolder.setStartTime();
        logger.info(
                "ContractEventCallback onPushEventLog  params: {}, status: {}, logs: {}, start:{}",
                getFilter().getParams(), status, logs, start);

        for (LogResult log : logs) {
            String cidStr = "";
            String result = "";
            ReqHistory reqHistory = null;
            int reqStatus = ReqStatusEnum.SUCCESS.getStatus();
            String error = "";
            String url = "";
            try {
                Bytes32 cid = CommonUtils.getBytes32FromEventLog(log.getLogParams(), LOG_ID);
                cidStr = Numeric.toHexString(cid.getValue());
                String argValue = CommonUtils.getStringFromEventLog(log.getLogParams(), LOG_ARG);
                String contractAddress = CommonUtils.getStringFromEventLog(log.getLogParams(), LOG_SENDER);
                String timestamp = CommonUtils.getStringFromEventLog(log.getLogParams(), LOG_TIMESTAMP);
                String datasource = CommonUtils.getStringFromEventLog(log.getLogParams(), LOG_DATASOURCE);
                int len = contractAddress.length();
                contractAddress = contractAddress.substring(1, len - 1);
                logger.info("==========cidStr:{} arg:{} contractAddress:{} ", cidStr, argValue, contractAddress);

                if (argValue.startsWith("\"")) {
                    int len1 = argValue.length();
                    argValue = argValue.substring(1, len1 - 1);
                }
                int left = argValue.indexOf("(");
                int right = argValue.indexOf(")");
                String format = argValue.substring(0, left);
                url = argValue.substring(left + 1, right);
                List<String> httpResultIndexList = subFiledValueForHttpResultIndex(argValue);

                String reqQuery = "";

                reqHistory = ReqHistory.build(cidStr, contractAddress, OracleVersionEnum.ORACLIZE_10000, SourceTypeEnum.URL, reqQuery, null);
                // save request to db
                logger.info("Save request:[{}:{}:{}] to db.", cidStr, contractAddress, reqQuery);
                this.reqHistoryRepository.save(reqHistory);

                //get data from url and update blockChain
                result = oracleService.getDataFromUrlAndUpChain(contractAddress, cid.getValue(), url, format, httpResultIndexList, chainId, groupId);

                logger.info("Request url:[{}:{}] callback success", cidStr, result);
            } catch (OracleException oe) {
                // oracle exception
                reqStatus = oe.getCodeAndMsg().getCode();
                error = String.format("%s,%s", oe.getCodeAndMsg().getMessage(), ExceptionUtils.getRootCauseMessage(oe));
                logger.error("Request url:[{}] oracle error:[{}]", url, error,oe);
            } catch (SocketTimeoutException e) {
                // socket error
                String errorMsg = ExceptionUtils.getRootCauseMessage(e);
                ReqStatusEnum reqStatusEnum = ReqStatusEnum.getBySocketErrorMsg(errorMsg);
                reqStatus = reqStatusEnum.getStatus();
                error = reqStatusEnum.format(errorMsg);
                logger.error("Request url:[{}] socket error:[{}]", url, error,e);
            } catch (RemoteCallException e) {
                // response error
                reqStatus = e.getStatus();
                error = e.getDetailMessage();
                logger.error("Request url:[{}] response error:[{}]", url, error,e);
            } catch (Exception e) {
                // other exception
                reqStatus = ReqStatusEnum.UNEXPECTED_EXCEPTION_ERROR.getStatus();
                error = ReqStatusEnum.UNEXPECTED_EXCEPTION_ERROR.format(ExceptionUtils.getRootCauseMessage(e));
                logger.error("Request url:[{}] unexpected exception error:[{}]", url, error, e);
            } finally {
                // check req history exists
                Optional<ReqHistory> reqHistoryOptional = this.reqHistoryRepository.findByReqId(cidStr);
                if (reqHistoryOptional.isPresent()) {
                    // update req history record
                    reqHistory = reqHistoryOptional.get();
                    reqHistory.setReqStatus(reqStatus);
                    if (StringUtils.isNotBlank(error)) {
                        reqHistory.setError(StringUtils.length(error) > MAX_ERROR_LENGTH ? StringUtils.substring(error, 0, MAX_ERROR_LENGTH) : error);
                    }
                    reqHistory.setProcessTime(System.currentTimeMillis() - ThreadLocalHolder.getStartTime());
                    reqHistory.setResult(result);

                    // save
                    this.reqHistoryRepository.save(reqHistory);
                } else {
                    logger.error("No request history:[{}:{}] inserted!!!", cidStr, url);
                }
            }
        }
    }


    @Override
    public LogResult transferLogToLogResult(Log log) {
        try {
            LogResult logResult = getDecoder().decodeEventLogReturnObject(log);
            return logResult;
        } catch (BaseException e) {
            logger.warn(" event log decode failed, log: {}", log);
            return null;
        }
    }


    /**
     * @param argValue
     * @return
     */
    private String subFiledValueForUrl(String argValue) {
        if (StringUtils.isBlank(argValue)) {
            logger.warn("argValue is empty");
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
            logger.warn("argValue is:{} ,return empty list", argValue);
            return Collections.EMPTY_LIST;
        }

        String resultIndex = argValue.substring(argValue.indexOf(").") + 2);

        String[] resultIndexArr = resultIndex.split("\\.");//.replaceAll("\\.", ",").split(",")
        List resultList = new ArrayList<>(resultIndexArr.length);
        Collections.addAll(resultList, resultIndexArr);
        return resultList;
    }


}
