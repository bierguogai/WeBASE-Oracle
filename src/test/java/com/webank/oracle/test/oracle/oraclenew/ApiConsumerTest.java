package com.webank.oracle.test.oracle.oraclenew;

import java.math.BigInteger;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.webank.oracle.base.enums.ContractTypeEnum;
import com.webank.oracle.base.properties.ConstantProperties;
import com.webank.oracle.base.properties.EventRegister;
import com.webank.oracle.contract.ContractDeploy;
import com.webank.oracle.test.oracle.base.BaseTest;
import com.webank.oracle.test.oracle.oraclenew.contract.APIConsumer;
import com.webank.oracle.transaction.oracle.OracleCore;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiConsumerTest extends BaseTest {

    @Test
    public void testOracleChain1() throws Exception {

        Web3j web3j = getWeb3j(eventRegisterProperties.getEventRegisters().get(0).getChainId(), 1);

        OracleCore oracleCore = OracleCore.deploy(web3j, credentials, ConstantProperties.GAS_PROVIDER).send();
        String orcleAddress = oracleCore.getContractAddress();
        System.out.println("orcleAddress: " + orcleAddress);
        APIConsumer apiConsumer = APIConsumer.deploy(web3j, credentials, ConstantProperties.GAS_PROVIDER, oracleCore.getContractAddress()).send();
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
    public void testApiConsumer() {
        try {
            EventRegister eventRegister = eventRegisterProperties.getEventRegisters().get(0);

            int chainId = eventRegister.getChainId();
            int groupId = eventRegister.getGroup();

            Optional<ContractDeploy> deployOptional =
                    this.contractDeployRepository.findByChainIdAndGroupIdAndContractType(chainId, groupId, ContractTypeEnum.ORACLE_CORE.getId());
            if (!deployOptional.isPresent()) {
                Assertions.fail();
                return;
            }

            String oracleCoreAddress = deployOptional.get().getContractAddress();
            log.info("oracle core address " + oracleCoreAddress);

            // asset
            Web3j web3j = getWeb3j(chainId, groupId);
            APIConsumer apiConsumer = APIConsumer.deploy(web3j, credentials, ConstantProperties.GAS_PROVIDER, oracleCoreAddress).send();
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
