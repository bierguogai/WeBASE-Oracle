package com.webank.oracle.event.vo;

import org.fisco.bcos.web3j.tx.txdecode.LogResult;

import com.webank.oracle.base.enums.OracleVersionEnum;
import com.webank.oracle.base.enums.SourceTypeEnum;
import com.webank.oracle.repository.domian.ReqHistory;

/**
 *
 */

public interface LogEvent {

    /**
     *
     * @param logResult
     */
    public void parse(LogResult logResult);


    /**
     *
     * @return
     */
    public ReqHistory convert(OracleVersionEnum oracleVersionEnum, SourceTypeEnum sourceTypeEnum);

}
