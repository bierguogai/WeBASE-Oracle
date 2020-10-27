/**
 *
 */


package com.webank.oracle.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OracleVersionEnum {
    _0(0,"eg. init version")
    ;

    private int id;
    private String description;

}
