package com.webank.oracle.test.oracle.old;

import com.webank.oracle.test.oracle.base.BaseTest;
import com.webank.oracle.test.oracle.random.RandomOracle;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.jupiter.api.Test;

public class OracleTestbak extends BaseTest {

    @Test
    public void testOracleChain1() throws Exception {

        Web3j web3j = getWeb3j(eventRegisterProperties.getEventRegisters().get(0).getChainId(),1);

        //fist  secretRegistty
//        com.webank.test.oracle.OraclizeCore oraliceCore = com.webank.test.oracle.OraclizeCore.deploy(web3j, credentials, contractGasProvider).send();
//        String orcleAddress = oraliceCore.getContractAddress();
        System.out.println("oracle core address " + eventRegisterProperties.getEventRegisters().get(0).getOracleCoreContractAddress());
        OraclizeCore oraliceCore = OraclizeCore.load(eventRegisterProperties.getEventRegisters().get(0).getOracleCoreContractAddress(), web3j,credentials, contractGasProvider);
        String orcleAddress = oraliceCore.getContractAddress();
       // System.out.println("oracleAddress: " + orcleAddress);
        // asset
        RandomOracle temperatureOracle = RandomOracle.deploy(web3j, credentials, contractGasProvider).send();
        temperatureOracle.getContractAddress();

        TransactionReceipt t = temperatureOracle.oracle_setNetwork(orcleAddress).send();

        System.out.println(t.getStatus());
        TransactionReceipt t1 = temperatureOracle.update().send();
        System.out.println(t1.getStatus()+ t1.getOutput());
        byte[] bytes1 = temperatureOracle.id().send();
        System.out.println("fff" + bytesToHex(bytes1));

    }

    @Test
    public void testOracleChain2() throws Exception {

        Web3j web3j = getWeb3j(eventRegisterProperties.getEventRegisters().get(1).getChainId(),1);

        //fist  secretRegistty
//        com.webank.test.oracle.OraclizeCore oraliceCore = com.webank.test.oracle.OraclizeCore.deploy(web3j, credentials, contractGasProvider).send();
//        String orcleAddress = oraliceCore.getContractAddress();
        System.out.println("oracle core address " + eventRegisterProperties.getEventRegisters().get(1).getOracleCoreContractAddress());
        OraclizeCore oraliceCore = OraclizeCore.load(eventRegisterProperties.getEventRegisters().get(1).getOracleCoreContractAddress(), web3j,credentials, contractGasProvider);
        String orcleAddress = oraliceCore.getContractAddress();
       // System.out.println("oracleAddress: " + orcleAddress);
        // asset
        RandomOracle temperatureOracle = RandomOracle.deploy(web3j, credentials, contractGasProvider).send();
        temperatureOracle.getContractAddress();

        TransactionReceipt t = temperatureOracle.oracle_setNetwork(orcleAddress).send();

        System.out.println(t.getStatus());
        TransactionReceipt t1 = temperatureOracle.update().send();
        byte[] bytes1 = temperatureOracle.id().send();
        System.out.println("fff" + bytesToHex(bytes1));

    }



//    public static void dealWithReceipt(TransactionReceipt transactionReceipt) {
//        if ("0x16".equals(transactionReceipt.getStatus()) && transactionReceipt.getOutput().startsWith("0x08c379a0")) {
//            System.out.println("0x16");
//            System.out.println(DecodeOutputUtils.decodeOutputReturnString0x16(transactionReceipt.getOutput()));
//        }
//    }


}
