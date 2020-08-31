import com.webank.oracle.Application;
import com.webank.oracle.base.exception.OracleException;
import com.webank.oracle.base.pojo.vo.ConstantCode;
import com.webank.oracle.base.properties.EventRegisterProperties;
import com.webank.oracle.transaction.OracleCore;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigInteger;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class OracleTestbak {
    @Autowired
    Map<Integer,Map<Integer, Web3j>> web3jMap;
    @Autowired
    EventRegisterProperties eventRegisterProperties;
    BigInteger gasPrice = new BigInteger("1");
    BigInteger gasLimit = new BigInteger("2100000000");
    ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
    //根据私钥导入账户
    Credentials credentials = Credentials.create("71bc07495a387ea072c64bc11bae63a86b724392d9c9276154ec441f0c0b1780");
    Credentials credentialsBob = Credentials.create("2");
    // 生成随机私钥使用下面方法；
    // Credentials credentialsBob =Credentials.create(Keys.createEcKeyPair());
    String Bob = "0x2b5ad5c4795c026514f8317c7a215e218dccd6cf";
    String Owner = "0x148947262ec5e21739fe3a931c29e8b84ee34a0f";

    String Alice = "0x1abc9fd9845cd5a0acefa72e4f40bcfd4136f864";

    @Test
    public void testOracleChain1() throws Exception {
        Credentials credentials = GenCredential.create();
        Web3j web3j = getWeb3j(eventRegisterProperties.getEventRegisters().get(0).getChainId(),1);

        //fist  secretRegistty
//        OraclizeCore oraliceCore = OraclizeCore.deploy(web3j, credentials, contractGasProvider).send();
//        String orcleAddress = oraliceCore.getContractAddress();
        System.out.println("oracle core address " + eventRegisterProperties.getEventRegisters().get(0).getContractAddress());
        OracleCore oraliceCore = OracleCore.load(eventRegisterProperties.getEventRegisters().get(0).getContractAddress(), web3j,credentials, contractGasProvider);
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
//        OraclizeCore oraliceCore = OraclizeCore.deploy(web3j, credentials, contractGasProvider).send();
//        String orcleAddress = oraliceCore.getContractAddress();
        System.out.println("oracle core address " + eventRegisterProperties.getEventRegisters().get(1).getContractAddress());
        OracleCore oraliceCore = OracleCore.load(eventRegisterProperties.getEventRegisters().get(1).getContractAddress(), web3j,credentials, contractGasProvider);
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

    private Web3j getWeb3j(int chainId, int groupId) throws Exception {
        Web3j web3j = web3jMap.get(chainId).get(groupId);
        if (web3j == null) {
            new OracleException(ConstantCode.GROUP_ID_NOT_EXIST);
        }
        return web3j;
//        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        System.out.println("start --------------------- ");
//        Service service = context.getBean(Service.class);
//        service.run();
//        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
//        channelEthereumService.setChannelService(service);
//
//        return Web3j.build(channelEthereumService, service.getGroupId());
    }

    private static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        String finalHex = new String(hexChars);
        return finalHex;
    }


//    public static void dealWithReceipt(TransactionReceipt transactionReceipt) {
//        if ("0x16".equals(transactionReceipt.getStatus()) && transactionReceipt.getOutput().startsWith("0x08c379a0")) {
//            System.out.println("0x16");
//            System.out.println(DecodeOutputUtils.decodeOutputReturnString0x16(transactionReceipt.getOutput()));
//        }
//    }


}
