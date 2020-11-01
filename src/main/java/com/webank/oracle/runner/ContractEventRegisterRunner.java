package com.webank.oracle.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.webank.oracle.event.callback.oraclize.OraclizeEventService;
import com.webank.oracle.event.callback.vrf.VRFEventService;

import lombok.extern.slf4j.Slf4j;

/**
 * Register contract event.
 */
@Order(2)
@Component
@Slf4j
public class ContractEventRegisterRunner implements ApplicationRunner {

    @Autowired private OraclizeEventService oraclizeEventService;
    @Autowired private VRFEventService vrfEventService;

    @Override
    public void run(ApplicationArguments args) {
        try{
//            oraclizeEventService.registerContractEvent();
            vrfEventService.registerContractEvent();
        }catch (Exception ex){
            log.error("ContractEventRegisterRunner exception",ex);
            System.exit(0);
        }

    }
}
