package com.webank.oracle.base.exception.event;

import com.webank.oracle.base.enums.ReqStatusEnum;
import com.webank.oracle.base.exception.EventBaseException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class NativeCallExceptionEvent extends EventBaseException {

    public NativeCallExceptionEvent(ReqStatusEnum reqStatusEnum, Object ... paramArray) {
        super(reqStatusEnum, paramArray);
    }
}
