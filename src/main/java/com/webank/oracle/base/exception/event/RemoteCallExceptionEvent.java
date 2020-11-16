package com.webank.oracle.base.exception.event;

import com.webank.oracle.base.enums.ReqStatusEnum;

public class RemoteCallExceptionEvent extends EventBaseException {
    public RemoteCallExceptionEvent(ReqStatusEnum reqStatusEnum, Object ... paramArray){
        super(reqStatusEnum,paramArray);
    }
}
