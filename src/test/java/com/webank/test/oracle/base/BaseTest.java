package com.webank.test.oracle.base;

import java.math.BigInteger;
import java.util.Map;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.webank.oracle.Application;
import com.webank.oracle.base.exception.OracleException;
import com.webank.oracle.base.pojo.vo.ConstantCode;
import com.webank.oracle.base.properties.EventRegisterProperties;

/**
 *
 */

@SpringBootTest(classes = Application.class)
@Transactional
public class BaseTest {
    @Autowired protected Map<Integer, Map<Integer, Web3j>> web3jMap;
    @Autowired protected EventRegisterProperties eventRegisterProperties;

    protected BigInteger gasPrice = new BigInteger("1");
    protected BigInteger gasLimit = new BigInteger("2100000000");

    protected ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);

    //根据私钥导入账户
    protected Credentials credentials = Credentials.create("71bc07495a387ea072c64bc11bae63a86b724392d9c9276154ec441f0c0b1780");
    protected Credentials credentialsBob = Credentials.create("2");

    // 生成随机私钥使用下面方法；
    // Credentials credentialsBob =Credentials.create(Keys.createEcKeyPair());
    protected String Bob = "0x2b5ad5c4795c026514f8317c7a215e218dccd6cf";
    protected String Owner = "0x148947262ec5e21739fe3a931c29e8b84ee34a0f";

    protected String Alice = "0x1abc9fd9845cd5a0acefa72e4f40bcfd4136f864";


    protected Web3j getWeb3j(int chainId, int groupId){
        Web3j web3j = web3jMap.get(chainId).get(groupId);
        if (web3j == null) {
            new OracleException(ConstantCode.GROUP_ID_NOT_EXIST);
        }
        return web3j;
    }

    protected static String bytesToHex(byte[] bytes) {
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

}