/**
 * Copyright 2014-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webank.oracle.event;

import com.webank.oracle.base.properties.EventRegister;
import com.webank.oracle.base.properties.EventRegisterProperties;
import com.webank.oracle.event.callback.ContractEventCallback;
import com.webank.oracle.transaction.OracleCore;
import com.webank.oracle.transaction.OracleService;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.channel.event.filter.EventLogUserParams;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.webank.oracle.transaction.OracleCore.LOG1_EVENT;

/**
 * event notify in message queue service
 * including new block event and contract event log push notify
 *
 * @author marsli
 */
@Slf4j
@Service
public class EventService {

    @Autowired
    Map<Integer, Map<Integer, org.fisco.bcos.channel.client.Service>> serviceMapWithChainId;
    @Autowired
    private OracleService oracleService;
    @Autowired
    private EventRegisterProperties eventRegisterProperties;


    /**
     * 在org.fisco.bcos.channel.client.Service中注册EventLogPush不会持久化
     * 如果重启了则需要重新注册
     * register ContractEventCallback
     */
    public void registerContractEvent() {
        List<EventRegister> eventRegisterList = eventRegisterProperties.getEventRegisters();
        for (int i = 0; i < eventRegisterList.size(); i++) {
            String contractAddress;
            EventRegister eventRegister = eventRegisterList.get(i);
            if (eventRegister.getContractAddress() == null || eventRegister.getContractAddress().equals("")) {
                contractAddress = oracleService.deployOracleCore(eventRegister.getChainId(),eventRegister.getGroup());
                log.info("chain {} group {} oracle-core has been deployed and  address is : {}" , eventRegister.getChainId(), eventRegister.getGroup(), contractAddress);
                eventRegister.setContractAddress(contractAddress);
            }

            // 传入abi作decoder:
            TransactionDecoder decoder = new TransactionDecoder(OracleCore.ABI);

            // init EventLogUserParams for register
            EventLogUserParams params = initSingleEventLogUserParams(eventRegister);
            ContractEventCallback callBack = new ContractEventCallback(oracleService, decoder,eventRegister.getChainId(),eventRegister.getGroup() );
            log.debug("&&&&&&registerContractEvent chainId: {} groupId:{},abi:{},params:{}",eventRegister.getChainId(), eventRegister.getGroup(), OracleCore.ABI, params);
            org.fisco.bcos.channel.client.Service service = serviceMapWithChainId.get(eventRegister.getChainId()).get(eventRegister.getGroup());
            service.registerEventLogFilter(params, callBack);
        }
    }


    /**
     * init EventLogUserParams with single contract address.
     *
     * @return
     */
    private EventLogUserParams initSingleEventLogUserParams(EventRegister eventRegister) {
        EventLogUserParams params = new EventLogUserParams();
        params.setFromBlock(eventRegister.getFromBlock());
        params.setToBlock(eventRegister.getToBlock());

        // addresses，设置为Java合约对象的地址
        List<String> addresses = new ArrayList<>();
        addresses.add(eventRegister.getContractAddress());
        params.setAddresses(addresses);
        List<Object> topics = new ArrayList<>();
        topics.add(EventEncoder.encode(LOG1_EVENT));
        params.setTopics(topics);

        return params;
    }
}
