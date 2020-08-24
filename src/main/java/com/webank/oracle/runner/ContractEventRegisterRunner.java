package com.webank.oracle.runner;

import com.webank.oracle.event.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Register contract event.
 */
@Order(2)
@Component
@Slf4j
public class ContractEventRegisterRunner implements ApplicationRunner {

    @Autowired
    private EventService eventService;

    @Override
    public void run(ApplicationArguments args) {
        try{
            eventService.registerContractEvent();
        }catch (Exception ex){
            log.error("ContractEventRegisterRunner exception",ex);
            System.exit(0);
        }

    }
}
