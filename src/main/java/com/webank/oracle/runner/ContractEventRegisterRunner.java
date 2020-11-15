package com.webank.oracle.runner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.webank.oracle.base.properties.EventRegister;
import com.webank.oracle.base.properties.EventRegisterProperties;
import com.webank.oracle.event.callback.oraclize.OraclizeEventCallback;
import com.webank.oracle.event.callback.vrf.VRFContractEventCallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ContractEventRegisterRunner {

    @Autowired
    private EventRegisterProperties eventRegisterProperties;
    @Autowired
    private ApplicationContext ctx;


    /**
     * 注册回调
     */
    @EventListener(ApplicationReadyEvent.class)
    public void init( ) {
        try{
            log.info("Register event listener call back...");
            List<EventRegister> eventRegisterList = eventRegisterProperties.getEventRegisters();
            for (int i = 0; i < eventRegisterList.size(); i++) {
                EventRegister eventRegister = eventRegisterList.get(i);
                // init OracleCore on this chain and group
                OraclizeEventCallback oraclizeEventCallback = ctx.getBean(OraclizeEventCallback.class, eventRegister.getChainId(), eventRegister.getGroup());
                oraclizeEventCallback.init(eventRegister);
                log.info("Deploy oracleCore contract:[{}]", eventRegister.getOracleCoreContractAddress());

                // init VRF on this chain and group
                VRFContractEventCallback vrfContractEventCallback = ctx.getBean(VRFContractEventCallback.class, eventRegister.getChainId(), eventRegister.getGroup());
                vrfContractEventCallback.init(eventRegister);
                log.info("Deploy vrf contract:[{}]", eventRegister.getVrfContractAddress());
            }
        }catch (Exception ex){
            log.error("ContractEventRegisterRunner exception",ex);
            System.exit(0);
        }

    }
}
