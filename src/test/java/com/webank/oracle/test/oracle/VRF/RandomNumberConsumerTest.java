package com.webank.oracle.test.oracle.VRF;

import java.math.BigInteger;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.webank.oracle.test.oracle.VRF.contract.RandomNumberConsumer;
import com.webank.oracle.test.oracle.base.BaseTest;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */

@Slf4j
public class RandomNumberConsumerTest extends BaseTest {

    @Test
    public void testVRFGetFromContract() {
        try {
            Web3j web3j = getWeb3j(eventRegisterProperties.getEventRegisters().get(0).getChainId(), 1);
            String coordinatorAddress = eventRegisterProperties.getEventRegisters().get(0).getVrfContractAddress();
            log.info("VRFCoordinator address " + coordinatorAddress);

            // 获取部署 coordinate 合约的密钥对
            Credentials credential = this.keyStoreService.getCredentials();

            // 获取部署 coordinate 合约的公钥 hash
            byte[] keyhashbyte = VRFTest.calculateTheHashOfPK(credential.getEcKeyPair().getPrivateKey().toString(16));

            // 部署随机数调用合约
            // keyhashbyte: 必须要用部署 coordinate 合约的公钥
            RandomNumberConsumer randomNumberConsumer = RandomNumberConsumer.deploy(web3j, credential, contractGasProvider, coordinatorAddress, keyhashbyte).send();
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

}