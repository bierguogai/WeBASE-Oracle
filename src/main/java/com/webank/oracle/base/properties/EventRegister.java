package com.webank.oracle.base.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * properties of event register.
 */
@Data
public class EventRegister {
    private int chainId = 1 ;
    private Integer group;
    private String contractAddress;
    private String fromBlock;
    private String toBlock;
    private String abiFile;
    private List<String> topicList;
}
