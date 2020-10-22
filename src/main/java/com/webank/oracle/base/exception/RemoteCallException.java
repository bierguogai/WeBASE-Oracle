package com.webank.oracle.base.exception;

import org.apache.commons.lang.ArrayUtils;

import com.webank.oracle.base.enums.ReqStatusEnum;

import lombok.Data;

@Data
public class RemoteCallException extends RuntimeException {
    private int status;
    private String detailMessage;

    private RemoteCallException(){}

    public static RemoteCallException build(ReqStatusEnum reqStatusEnum,String ... paramArray){
        RemoteCallException remoteCallException = new RemoteCallException();
        remoteCallException.setStatus(reqStatusEnum.getStatus());
        if (ArrayUtils.isNotEmpty(paramArray)){
            remoteCallException.setDetailMessage(reqStatusEnum.format(paramArray));
        }else{
            remoteCallException.setDetailMessage(reqStatusEnum.getFormat());
        }

        return remoteCallException;
    }
}
