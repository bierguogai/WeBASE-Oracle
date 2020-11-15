package com.webank.oracle.test.oracle.VRF;

import java.math.BigInteger;

import com.webank.oracle.test.oracle.base.BaseTest;
import com.webank.oracle.test.oracle.oraclenew.APIConsumer;
import com.webank.oracle.transaction.oracle.OracleCore;
import org.apache.commons.lang3.StringUtils;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.webank.oracle.transaction.vrf.RandomNumberConsumer;
import com.webank.oracle.transaction.vrf.VRFCoordinator;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */

@Slf4j
public class VRFEventTest extends BaseTest {

    @Test
    public void testVRFGetFromContract() {
        try {
            Web3j web3j = getWeb3j(eventRegisterProperties.getEventRegisters().get(0).getChainId(), 1);
            log.info("VRFCoordinator address " + eventRegisterProperties.getEventRegisters().get(0).getVrfContractAddress());

            // 获取部署 coordinate 合约的密钥对
            Credentials credential = this.keyStoreService.getCredentials();

            // 获取 coordinate 合约的地址
            String coordinateAddress = VRFCoordinator.load(eventRegisterProperties.getEventRegisters().get(0).getVrfContractAddress(), web3j, credential, contractGasProvider).getContractAddress();

            // 获取部署 coordinate 合约的公钥 hash
            byte[] keyhashbyte = VRFTest.calculateTheHashOfPK(credential.getEcKeyPair().getPrivateKey().toString(16));

            // 部署随机数调用合约
            // keyhashbyte: 必须要用部署 coordinate 合约的公钥
            RandomNumberConsumer randomNumberConsumer = RandomNumberConsumer.deploy(web3j, credential, contractGasProvider, coordinateAddress, keyhashbyte).send();
            String consumerContractAddress = randomNumberConsumer.getContractAddress();
            System.out.println("consumer address: " + consumerContractAddress);

            // 请求随机数
            System.out.println("consumer start a query ....... ");
            TransactionReceipt randomT = randomNumberConsumer.getRandomNumber(new BigInteger("1")).send();
            System.out.println(randomT.getStatus());
            System.out.println(randomT.getOutput());
            System.out.println("consumer query reqId: " + randomT.getOutput());

            Thread.sleep(10000);

            // 获取生成的随机数结果
            BigInteger send = randomNumberConsumer.randomResult().send();
            log.info("Request id:[{}], random:[{}]", randomT.getOutput(), send.toString(16));

            Assertions.assertTrue(send.compareTo(BigInteger.ZERO) != 0);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @Test
    public void testNewOracleCoreRandomGet() {
        try {

            Web3j web3j = getWeb3j(eventRegisterProperties.getEventRegisters().get(0).getChainId(), 1);

            log.info("oracle core address " + eventRegisterProperties.getEventRegisters().get(0).getOracleCoreContractAddress());


            OracleCore oracleCore = OracleCore.deploy(web3j, credentials, contractGasProvider).send();
            String oracleAddress = oracleCore.getContractAddress();
            log.info("oracleCore address: [{}]", oracleAddress);
            // asset
            APIConsumer apiConsumer = APIConsumer.deploy(web3j, credentials, contractGasProvider, oracleCore.getContractAddress()).send();
            String apiConsumerAddress = apiConsumer.getContractAddress();
            log.info("Deploy APIConsumer contract:[{}]", apiConsumerAddress);

            TransactionReceipt t1 = apiConsumer.request().send();
            log.info("Generate random receipt: [{}:{}]", t1.getStatus(), t1.getOutput());

            Thread.sleep(5000);

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