/**
 *
 */


package com.webank.oracle.base.enums;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReqStatusEnum {
    SUCCESS(0, "success"),
    UNKNOWN_SOCKET_ERROR(1000_001, "Unknown socket error:[%s]."),
    HOST_UNAVAILABLE_ERROR(1000_002, "Remote host is unavailable:[%s]."),
    WRITE_TIMEOUT_ERROR(1000_003, "Write data to remote host timeout:[%s]."),
    READ_TIMEOUT_ERROR(1000_004, "Read from remote host timeout:[%s]."),

    _404_NOT_FOUND_ERROR(1000_101, "Request url not found:[404]."),
    _500_SERVER_ERROR(1000_102, "Remote server internal error:[5005]."),
    OTHER_CODE_ERROR(1000_103, "Http code:[%s] from remote, not 200."),

    EMPTY_RESPONSE_ERROR(1000_201, "Empty response from remote."),
    RESULT_FORMAT_ERROR(1000_202, "Return data format:[%s] error:[%s]."),

    UNEXPECTED_EXCEPTION_ERROR(1000_302, "Unexpected exception:[%s]."),
    ;

    private int status;
    private String format;


    /**
     *
     * @param args
     * @return
     */
    public String format(String ... args){
        return String.format(this.format, args );
    }

    /**
     *
     * @param errorMsg
     * @return
     */
    public static ReqStatusEnum getBySocketErrorMsg(String errorMsg){
        if (StringUtils.containsIgnoreCase(errorMsg, "connect")) {
            return HOST_UNAVAILABLE_ERROR;
        } else if (StringUtils.containsIgnoreCase(errorMsg, "read")) {
            return READ_TIMEOUT_ERROR;
        } else if (StringUtils.containsIgnoreCase(errorMsg, "write")) {
            return WRITE_TIMEOUT_ERROR;
        }
        return UNKNOWN_SOCKET_ERROR;
    }
}