/**
 *
 */


package com.webank.oracle.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SourceTypeEnum {
    URL(0, "url","eg. init version")
    ;

    private int id;
    private String type;
    private String description;


}
