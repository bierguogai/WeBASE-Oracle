package com.webank.oracle.base.exception.event;

import com.webank.oracle.base.enums.ReqStatusEnum;
import com.webank.oracle.base.exception.EventBaseException;

public class PushEventLogExceptionEvent extends EventBaseException {
    public PushEventLogExceptionEvent(ReqStatusEnum reqStatusEnum, Object ... paramArray){
        super(reqStatusEnum,paramArray);
    }
}
