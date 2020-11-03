package com.webank.test.oracle.oraclenew;

import com.webank.test.oracle.OraclizeCore;
import com.webank.test.oracle.base.BaseTest;
import com.webank.test.oracle.random.RandomOracle;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.jupiter.api.Test;

public class OracleTest extends BaseTest {

    @Test
    public void testOracleChain1() throws Exception {

        Web3j web3j = getWeb3j(eventRegisterProperties.getEventRegisters().get(0).getChainId(),1);

        OracleCore oracleCore = OracleCore.deploy(web3j, credentials, contractGasProvider).send();
        String orcleAddress = oracleCore.getContractAddress();
        System.out.println("orcleAddress: " + orcleAddress);
        APIConsumer apiConsumer = APIConsumer.deploy( web3j,credentials, contractGasProvider, oracleCore.getContractAddress()).send();
        String apiConsumerAddress = apiConsumer.getContractAddress();
        System.out.println("apiConsumerAddress: " + apiConsumerAddress);

        TransactionReceipt t = apiConsumer.request().send();
        System.out.println(t.getStatus());
        System.out.println(t.getOutput());

        OracleCore.OracleRequestEventResponse requestedEventResponse =  oracleCore.getOracleRequestEvents(t).get(0);

        System.out.println(requestedEventResponse.url);
        System.out.println(requestedEventResponse._timesAmount);
        System.out.println(requestedEventResponse.callbackAddr);
        System.out.println(bytesToHex(requestedEventResponse.requestId));


    }



}
