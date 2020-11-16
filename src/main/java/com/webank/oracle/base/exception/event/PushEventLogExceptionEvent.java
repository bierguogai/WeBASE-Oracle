package com.webank.oracle.base.exception.event;

import com.webank.oracle.base.enums.ReqStatusEnum;

public class PushEventLogExceptionEvent extends EventBaseException {
    public PushEventLogExceptionEvent(ReqStatusEnum reqStatusEnum, Object ... paramArray){
        super(reqStatusEnum,paramArray);
    }
}
