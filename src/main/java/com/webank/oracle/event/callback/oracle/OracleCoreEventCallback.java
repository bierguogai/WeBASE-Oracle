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

package com.webank.oracle.event.callback.oracle;

import org.fisco.bcos.web3j.tx.txdecode.LogResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.webank.oracle.base.enums.OracleVersionEnum;
import com.webank.oracle.base.enums.SourceTypeEnum;
import com.webank.oracle.base.properties.EventRegister;
import com.webank.oracle.event.callback.AbstractEventCallback;
import com.webank.oracle.event.vo.oraclize.OracleCoreLogResult;
import com.webank.oracle.transaction.oracle.OracleCore;
import com.webank.oracle.event.service.oracle.OracleService;

import lombok.extern.slf4j.Slf4j;


/**
 * 从callback中获取事件推送过来的请求地址，再请求该地址获取数据上链。
 */
@Component
@Scope("prototype")
@Slf4j
public class OracleCoreEventCallback extends AbstractEventCallback {

    @Autowired private OracleService oracleService;

    /**
     * @param chainId
     * @param groupId
     */
    public OracleCoreEventCallback(int chainId, int groupId) {
        super(OracleCore.ABI, OracleCore.ORACLEREQUEST_EVENT, chainId, groupId);
    }

    @Override
    public String loadOrDeployContract(int chainId, int group) {
        return oracleService.loadContractAddress(chainId,group);
    }

    @Override
    public String processLog(int status, LogResult logResult) throws Exception {
        OracleCoreLogResult oracleCoreLogResult = new OracleCoreLogResult(logResult);

        log.info("Process log event:[{}]", oracleCoreLogResult);

        this.reqHistoryRepository.save(oracleCoreLogResult.convert(OracleVersionEnum.ORACLIZE_10000, SourceTypeEnum.URL));
        log.info("Save request:[{}:{}:{}] to db.", oracleCoreLogResult.getCallbackAddress(),
                oracleCoreLogResult.getRequestId(), oracleCoreLogResult.getUrl());

        //get data from url and update blockChain
        return oracleService.getResultAndUpToChain(chainId, groupId, oracleCoreLogResult);
    }

    @Override
    public void setContractAddress(EventRegister eventRegister, String contractAddress) {
        eventRegister.setOracleCoreContractAddress(contractAddress);
    }

    @Override
    public String getContractAddress(EventRegister eventRegister){
        return eventRegister.getOracleCoreContractAddress();
    }

//    /**
//     * 根据Log对象中的blockNumber，transactionIndex，logIndex进行去重
//     *
//     * @param status
//     * @param logs
//     */
//    public void onPushEventLog(int status, LogResult) {
//        long start = ThreadLocalHolder.setStartTime();
//        logger.info(
//                "ContractEventCallback onPushEventLog  params: {}, status: {}, logs: {}, start:{}",
//                getFilter().getParams(), status, logs, start);
//
//        for (LogResult log : logs) {
//            String cidStr = "";
//            String result = "";
//            ReqHistory reqHistory = null;
//            int reqStatus = ReqStatusEnum.SUCCESS.getStatus();
//            String error = "";
//            String url = "";
//            try {
//                Bytes32 cid = CommonUtils.getBytes32FromEventLog(log.getLogParams(), LOG_ID);
//                cidStr = Numeric.toHexString(cid.getValue());
//                String argValue = CommonUtils.getStringFromEventLog(log.getLogParams(), LOG_ARG);
//                String contractAddress = CommonUtils.getStringFromEventLog(log.getLogParams(), LOG_SENDER);
//                String timestamp = CommonUtils.getStringFromEventLog(log.getLogParams(), LOG_TIMESTAMP);
//                String datasource = CommonUtils.getStringFromEventLog(log.getLogParams(), LOG_DATASOURCE);
//                int len = contractAddress.length();
//                contractAddress = contractAddress.substring(1, len - 1);
//
//                if (argValue.startsWith("\"")) {
//                    int len1 = argValue.length();
//                    argValue = argValue.substring(1, len1 - 1);
//                }
//                int left = argValue.indexOf("(");
//                int right = argValue.indexOf(")");
//                String format = argValue.substring(0, left);
//                url = argValue.substring(left + 1, right);
//                List<String> httpResultIndexList = subFiledValueForHttpResultIndex(argValue);
//
//                String reqQuery = "";
//
//                // save request to db
//                logger.info("Save request:[{}:{}:{}] to db.", cidStr, contractAddress, reqQuery);
//                this.reqHistoryRepository.save(reqHistory);
//
//                logger.info("Request url:[{}:{}] callback success", cidStr, result);
//            } catch (OracleException oe) {
//                // oracle exception
//                reqStatus = oe.getCodeAndMsg().getCode();
//                error = String.format("%s,%s", oe.getCodeAndMsg().getMessage(), ExceptionUtils.getRootCauseMessage(oe));
//                logger.error("Request url:[{}] oracle error:[{}]", url, error, oe);
//            } catch (SocketTimeoutException e) {
//                // socket error
//                String errorMsg = ExceptionUtils.getRootCauseMessage(e);
//                ReqStatusEnum reqStatusEnum = ReqStatusEnum.getBySocketErrorMsg(errorMsg);
//                reqStatus = reqStatusEnum.getStatus();
//                error = reqStatusEnum.format(errorMsg);
//                logger.error("Request url:[{}] socket error:[{}]", url, error, e);
//            } catch (RemoteCallException e) {
//                // response error
//                reqStatus = e.getStatus();
//                error = e.getDetailMessage();
//                logger.error("Request url:[{}] response error:[{}]", url, error, e);
//            } catch (Exception e) {
//                // other exception
//                reqStatus = ReqStatusEnum.UNEXPECTED_EXCEPTION_ERROR.getStatus();
//                error = ReqStatusEnum.UNEXPECTED_EXCEPTION_ERROR.format(ExceptionUtils.getRootCauseMessage(e));
//                logger.error("Request url:[{}] unexpected exception error:[{}]", url, error, e);
//            } finally {
//                // check req history exists
//                Optional<ReqHistory> reqHistoryOptional = this.reqHistoryRepository.findByReqId(cidStr);
//                if (reqHistoryOptional.isPresent()) {
//                    // update req history record
//                    reqHistory = reqHistoryOptional.get();
//                    reqHistory.setReqStatus(reqStatus);
//                    if (StringUtils.isNotBlank(error)) {
//                        reqHistory.setError(StringUtils.length(error) > MAX_ERROR_LENGTH ? StringUtils.substring(error, 0, MAX_ERROR_LENGTH) : error);
//                    }
//                    reqHistory.setProcessTime(System.currentTimeMillis() - ThreadLocalHolder.getStartTime());
//                    reqHistory.setResult(result);
//
//                    // save
//                    this.reqHistoryRepository.save(reqHistory);
//                } else {
//                    logger.error("No request history:[{}:{}] inserted!!!", cidStr, url);
//                }
//            }
//        }
//    }
//
//

}
