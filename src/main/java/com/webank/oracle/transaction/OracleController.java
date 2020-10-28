package com.webank.oracle.transaction;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webank.oracle.base.properties.EventRegister;
import com.webank.oracle.base.properties.EventRegisterProperties;

@RestController
@RequestMapping(value = "/oracle")
public class OracleController {

    @Autowired
    private EventRegisterProperties eventRegisterProperties;

    @GetMapping("/oracle-core-address")
    public String getOracleCoreAddress(@RequestParam(defaultValue = "1") int chainId,
                                       @RequestParam(defaultValue = "1") int groupId) {
        EventRegister eventRegister = eventRegisterProperties.getEventRegisters().stream().filter(x -> x.getChainId() == (chainId) && x.getGroup() == groupId).findAny().get();
        if (eventRegister != null) {
            return eventRegister.getContractAddress();
        } else
            return null;
    }

}
