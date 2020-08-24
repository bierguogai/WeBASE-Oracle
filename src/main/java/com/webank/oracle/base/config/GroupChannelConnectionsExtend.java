package com.webank.oracle.base.config;

import lombok.Data;
import org.fisco.bcos.channel.handler.GroupChannelConnectionsConfig;

@Data
public class GroupChannelConnectionsExtend  extends  GroupChannelConnectionsConfig{

    private int chainId;
}
