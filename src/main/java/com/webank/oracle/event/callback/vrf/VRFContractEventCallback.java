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

package com.webank.oracle.event.callback.vrf;

import static com.webank.oracle.base.properties.ConstantProperties.MAX_ERROR_LENGTH;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.tx.txdecode.BaseException;
import org.fisco.bcos.web3j.tx.txdecode.LogResult;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;

import com.webank.oracle.base.enums.OracleVersionEnum;
import com.webank.oracle.base.enums.ProofTypeEnum;
import com.webank.oracle.base.enums.ReqStatusEnum;
import com.webank.oracle.base.enums.SourceTypeEnum;
import com.webank.oracle.base.exception.NativeCallException;
import com.webank.oracle.base.exception.OracleException;
import com.webank.oracle.base.utils.CommonUtils;
import com.webank.oracle.base.utils.ThreadLocalHolder;
import com.webank.oracle.repository.ReqHistoryRepository;
import com.webank.oracle.repository.domian.ReqHistory;
import com.webank.oracle.transaction.vrf.VRFService;

import lombok.extern.slf4j.Slf4j;

/**
 * 从callback中获取事件推送过来的请求地址，再请求该地址获取数据上链。
 */
@Slf4j
public class VRFContractEventCallback extends EventLogPushWithDecodeCallback {

    private static final String LOG_KEY_HASH = "keyHash";
    private static final String LOG_SEED = "seed";
    private static final String LOG_BLOCK_NUMBER = "blockNumber";
    private static final String LOG_SENDER = "sender";
    private static final String LOG_REQUEST_ID = "requestID";
    private static final String LOG_SEED_BLOCK_NUM = "seedAndBlockNum";

    private VRFService vrfService;

    private ReqHistoryRepository reqHistoryRepository;

    private int chainId;
    private int groupId;

    public VRFContractEventCallback(VRFService vrfService, ReqHistoryRepository reqHistoryRepository,
                                    TransactionDecoder decoder, int chainId, int groupId) {
        // onPush will call father class's decoder, init EventLogPushWithDecodeCallback's decoder
        this.setDecoder(decoder);
        this.vrfService = vrfService;
        this.chainId = chainId;
        this.groupId = groupId;
        this.reqHistoryRepository = reqHistoryRepository;
    }


    /**
     * 根据Log对象中的 requestId 进行去重
     *
     * @param status
     * @param logs
     */
    @Override
    public void onPushEventLog(int status, List<LogResult> logs) {
        long start = ThreadLocalHolder.setStartTime();
        log.info("ContractEventCallback onPushEventLog  params: {}, status: {}, logs: {}, start:{}",
                getFilter().getParams(), status, logs, start);

        for (LogResult logResult : logs) {
            String requestID = "";
            String seedAndBlockNum = "";

            String result = "";

            ReqHistory reqHistory = null;
            int reqStatus = ReqStatusEnum.SUCCESS.getStatus();
            String error = "";

            try {
                String keyHash = CommonUtils.byte32LogToString(logResult.getLogParams(), LOG_KEY_HASH);
                BigInteger seed = CommonUtils.getBigIntegerFromEventLog(logResult.getLogParams(), LOG_SEED);
                BigInteger blockNumber = CommonUtils.getBigIntegerFromEventLog(logResult.getLogParams(), LOG_BLOCK_NUMBER);
                String sender = CommonUtils.getStringFromEventLog(logResult.getLogParams(), LOG_SENDER);
                requestID = CommonUtils.byte32LogToString(logResult.getLogParams(), LOG_REQUEST_ID);
                seedAndBlockNum = CommonUtils.byte32LogToString(logResult.getLogParams(), LOG_SEED_BLOCK_NUM);

                // log
                log.info("Receive a event:[{}:{}:{}:{}:{}]", requestID, keyHash, seed, blockNumber, sender, seedAndBlockNum);

                reqHistory = ReqHistory.build(requestID, sender, OracleVersionEnum.VRF_20000, SourceTypeEnum.VRF, seedAndBlockNum, null);
                // save request to db
                log.info("Save request:[{}:{}:{}] to db.", requestID, sender, seedAndBlockNum);
                this.reqHistoryRepository.save(reqHistory);

                //get data from url and update blockChain
                result = vrfService.generateAndUpChain(requestID, keyHash, seed, blockNumber, sender, seedAndBlockNum, chainId, groupId);

                log.info("Call VRF requestID:[{}:{}] callback success", requestID, result);
            } catch (OracleException oe) {
                // oracle exception
                reqStatus = oe.getCodeAndMsg().getCode();
                error = String.format("%s,%s", oe.getCodeAndMsg().getMessage(), ExceptionUtils.getRootCauseMessage(oe));
                log.error("Call VRF requestID:[{}] oracle error:[{}]", requestID, error,oe);
            } catch (NativeCallException e) {
                // response error
                reqStatus = e.getStatus();
                error = e.getDetailMessage();
                log.error("Call VRF requestID:[{}] native call error:[{}]", requestID, error,e);
            } catch (Exception e) {
                // other exception
                reqStatus = ReqStatusEnum.UNEXPECTED_EXCEPTION_ERROR.getStatus();
                error = ReqStatusEnum.UNEXPECTED_EXCEPTION_ERROR.format(ExceptionUtils.getRootCauseMessage(e));
                log.error("Request:[{}] unexpected exception error:[{}]", requestID, error,e);
            } finally {
                // check req history exists
                Optional<ReqHistory> reqHistoryOptional = this.reqHistoryRepository.findByReqId(requestID);
                if (reqHistoryOptional.isPresent()) {
                    // update req history record
                    reqHistory = reqHistoryOptional.get();
                    reqHistory.setReqStatus(reqStatus);
                    if (StringUtils.isNotBlank(error)) {
                        reqHistory.setError(StringUtils.length(error) > MAX_ERROR_LENGTH ? StringUtils.substring(error, 0, MAX_ERROR_LENGTH) : error);
                    }
                    reqHistory.setProcessTime(System.currentTimeMillis() - ThreadLocalHolder.getStartTime());
                    reqHistory.setResult(result);
                    reqHistory.setProof(result);
                    reqHistory.setProofType(ProofTypeEnum.SIGN.getId());

                    // save
                    this.reqHistoryRepository.save(reqHistory);
                } else {
                    log.error("No request history:[{}:{}] inserted!!!", requestID, seedAndBlockNum);
                }
            }
        }
    }


    @Override
    public LogResult transferLogToLogResult(Log logInfo) {
        try {
            LogResult logResult = getDecoder().decodeEventLogReturnObject(logInfo);
            return logResult;
        } catch (BaseException e) {
            log.error(" event log decode failed, log: {}", logInfo, e);
            return null;
        }
    }
}
