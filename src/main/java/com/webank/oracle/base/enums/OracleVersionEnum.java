/**
 *
 */


package com.webank.oracle.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OracleVersionEnum {
    ORACLIZE_10000(10000,"eg. Oraclize init version"),




    VRF_20000(20000,"eg. VRF init version")
    ;

    private int id;
    private String description;

}
