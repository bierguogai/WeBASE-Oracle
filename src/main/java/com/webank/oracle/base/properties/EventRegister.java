package com.webank.oracle.base.properties;

import java.util.List;

import lombok.Data;

/**
 * properties of event register.
 */
@Data
public class EventRegister {
    private int chainId = 1;
    private Integer group = 1;
    private String contractAddress;
    private String fromBlock = "latest";
    private String toBlock = "latest";
    private String abiFile;
    private List<String> topicList;
}
