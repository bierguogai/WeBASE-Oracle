package com.webank.oracle.event.vo.oraclize;

import java.math.BigInteger;

import org.fisco.bcos.web3j.tx.txdecode.LogResult;

import com.webank.oracle.base.enums.OracleVersionEnum;
import com.webank.oracle.base.enums.SourceTypeEnum;
import com.webank.oracle.base.utils.CommonUtils;
import com.webank.oracle.event.vo.BaseLogResult;
import com.webank.oracle.repository.domian.ReqHistory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */

@Slf4j
@Getter
@Setter
public class OraclizeLogResult extends BaseLogResult {

    private static final String LOG_CALLBACK_ADDR = "callbackAddr";
    private static final String LOG_URL = "url";
    private static final String LOG_TIMES_AMOUNT = "_timesAmount";
    private static final String LOG_EXPIRATION = "expiration";

    private String callbackAddress;
    private String url;
    private BigInteger expiration;

    /**
     * Multiply the result by 1000000000000000000 to remove decimals
     */
    private BigInteger timesAmount;

    public OraclizeLogResult(LogResult logResult) {
        super(logResult);
    }

    @Override
    public void parse(LogResult logResult) {
        callbackAddress = CommonUtils.getStringFromEventLog(logResult.getLogParams(), LOG_CALLBACK_ADDR);
        url = CommonUtils.getStringFromEventLog(logResult.getLogParams(), LOG_URL);
        timesAmount = CommonUtils.getBigIntegerFromEventLog(logResult.getLogParams(), LOG_TIMES_AMOUNT);
        expiration = CommonUtils.getBigIntegerFromEventLog(logResult.getLogParams(), LOG_EXPIRATION);
    }

    @Override
    public ReqHistory convert(OracleVersionEnum oracleVersionEnum, SourceTypeEnum sourceTypeEnum) {
        // TODO. save timesAmount??
        return ReqHistory.build(requestId, callbackAddress, oracleVersionEnum, sourceTypeEnum, url);
    }
}