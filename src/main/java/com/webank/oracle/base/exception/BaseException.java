package com.webank.oracle.base.exception;

import org.apache.commons.lang.ArrayUtils;

import com.webank.oracle.base.enums.ReqStatusEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class BaseException extends RuntimeException {
    private int status;
    private String detailMessage;

    public BaseException(ReqStatusEnum reqStatusEnum, Object ... paramArray){
        status = reqStatusEnum.getStatus();
        if (ArrayUtils.isNotEmpty(paramArray)){
            detailMessage = reqStatusEnum.format(paramArray);
        }else{
            detailMessage = reqStatusEnum.getFormat();
        }
    }
}
