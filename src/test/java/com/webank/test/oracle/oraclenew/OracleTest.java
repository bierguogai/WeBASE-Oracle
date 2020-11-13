package com.webank.test.oracle.oraclenew;

import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.webank.oracle.transaction.oraclize.OracleCore;
import com.webank.test.oracle.OraclizeCore;
import com.webank.test.oracle.base.BaseTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OracleTest extends BaseTest {

    @Test
    public void testOracleChain1() throws Exception {

        Web3j web3j = getWeb3j(eventRegisterProperties.getEventRegisters().get(0).getChainId(), 1);

        OracleCore oracleCore = OracleCore.deploy(web3j, credentials, contractGasProvider).send();
        String orcleAddress = oracleCore.getContractAddress();
        System.out.println("orcleAddress: " + orcleAddress);
        APIConsumer apiConsumer = APIConsumer.deploy(web3j, credentials, contractGasProvider, oracleCore.getContractAddress()).send();
        String apiConsumerAddress = apiConsumer.getContractAddress();
        System.out.println("apiConsumerAddress: " + apiConsumerAddress);

        TransactionReceipt t = apiConsumer.request().send();
        System.out.println(t.getStatus());
        System.out.println(t.getOutput());

        OracleCore.OracleRequestEventResponse requestedEventResponse = oracleCore.getOracleRequestEvents(t).get(0);

        System.out.println(requestedEventResponse.url);
        System.out.println(requestedEventResponse._timesAmount);
        System.out.println(requestedEventResponse.callbackAddr);
        System.out.println(bytesToHex(requestedEventResponse.requestId));


    }

    @Test
    public void testNewOraclizeRandomGet() {
        try {

            Web3j web3j = getWeb3j(eventRegisterProperties.getEventRegisters().get(0).getChainId(), 1);

            log.info("oracle core address " + eventRegisterProperties.getEventRegisters().get(0).getOraclizeContractAddress());

            OraclizeCore oralizeCore = OraclizeCore.load(eventRegisterProperties.getEventRegisters().get(0).getOraclizeContractAddress(), web3j, credentials, contractGasProvider);
            String oracleAddress = oralizeCore.getContractAddress();

            // asset
            APIConsumer apiConsumer = APIConsumer.deploy(web3j, credentials, contractGasProvider, oralizeCore.getContractAddress()).send();
            String apiConsumerAddress = apiConsumer.getContractAddress();
            log.info("Deploy APIConsumer contract:[{}]", apiConsumerAddress);

            TransactionReceipt t1 = apiConsumer.request().send();
            log.info("Generate random receipt: [{}:{}]", t1.getStatus(), t1.getOutput());

            Thread.sleep(10000);

            TransactionReceipt random = apiConsumer.getResult().send();
            log.info("Random:[{}]", random.getOutput());

            Assertions.assertTrue(StringUtils.isNotBlank(random.getOutput()));
            BigInteger result = new BigInteger(random.getOutput().substring(2),16);
            Assertions.assertTrue(result.compareTo(BigInteger.ZERO) != 0);
            log.info("Random result:[{}]", result);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }


}
