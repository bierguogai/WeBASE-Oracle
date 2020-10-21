package com.webank.test.oracle.random;

import org.apache.commons.lang3.StringUtils;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.webank.test.oracle.OraclizeCore;
import com.webank.test.oracle.base.BaseTest;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */

@Slf4j
public class RandomOracleTest extends BaseTest {

    @Test
    public void testRandomGet() {
        try {

            Web3j web3j = getWeb3j(eventRegisterProperties.getEventRegisters().get(0).getChainId(), 1);

            //fist  secretRegistty
//        com.webank.test.oracle.OraclizeCore oraliceCore = com.webank.test.oracle.OraclizeCore.deploy(web3j, credentials, contractGasProvider).send();
//        String orcleAddress = oraliceCore.getContractAddress();
            log.info("oracle core address " + eventRegisterProperties.getEventRegisters().get(0).getContractAddress());

            OraclizeCore oraliceCore = OraclizeCore.load(eventRegisterProperties.getEventRegisters().get(0).getContractAddress(), web3j, credentials, contractGasProvider);
            String oracleAddress = oraliceCore.getContractAddress();

            // asset
            RandomOracle temperatureOracle = RandomOracle.deploy(web3j, credentials, contractGasProvider).send();
            temperatureOracle.getContractAddress();

            TransactionReceipt t = temperatureOracle.oracle_setNetwork(oracleAddress).send();
            log.info("Set oracle address:{}", t.getStatus());

            TransactionReceipt t1 = temperatureOracle.update().send();
            log.info("Generate random receipt: [{}:{}]", t1.getStatus(), t1.getOutput());

            Thread.sleep(10000);

            byte[] requestId = temperatureOracle.id().send();
            String random = temperatureOracle.get().send();
            log.info("Request id:[fff{}], random:[{}]", bytesToHex(requestId), random);

            Assertions.assertTrue(StringUtils.isNotBlank(random));
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }
}