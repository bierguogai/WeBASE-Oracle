package com.webank.oracle.base.exception;

import com.webank.oracle.base.enums.ReqStatusEnum;

import lombok.Data;

@Data
public class NativeCallException extends BaseException {

    public NativeCallException(ReqStatusEnum reqStatusEnum, String... paramArray) {
        super(reqStatusEnum, paramArray);
    }
}
