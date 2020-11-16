package com.webank.oracle.event.service;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;

import com.webank.oracle.base.enums.ContractTypeEnum;
import com.webank.oracle.base.exception.OracleException;
import com.webank.oracle.base.pojo.vo.ConstantCode;
import com.webank.oracle.base.utils.DecodeOutputUtils;
import com.webank.oracle.event.vo.BaseLogResult;
import com.webank.oracle.http.HttpService;
import com.webank.oracle.keystore.KeyStoreService;
import com.webank.oracle.contract.ContractDeployRepository;
import com.webank.oracle.contract.ContractDeploy;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */

@Slf4j
public abstract class AbstractCoreService {

    /**
     * 链 ID 和群组对应的合约地址
     */
    protected Map<String, String> contractAddressMap = new ConcurrentHashMap<>();

    @Autowired protected Map<Integer, Map<Integer, Web3j>> web3jMap;
    @Autowired protected KeyStoreService keyStoreService;
    @Autowired protected HttpService httpService;
    @Autowired protected ContractDeployRepository contractDeployRepository;

    protected BigInteger gasPrice = new BigInteger("1");
    protected BigInteger gasLimit = new BigInteger("2100000000");
    protected ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);

    /**
     * 返回当前合约类型
     *
     * @return
     */
    public abstract ContractTypeEnum getContractType();


    /**
     * 部署合约
     *
     * @return
     */
     protected abstract String deployContract(int chainId, int group);

    /**
     * 获取结果并上链
     *
     * @return
     */
    public abstract String getResultAndUpToChain(int chainId, int groupId, BaseLogResult baseLogResult) throws Exception;

    /**
     * @param chainId
     * @param groupId
     * @param contractAddress
     * @param baseLogResult
     * @param result
     * @return
     */
    public abstract void fulfill(int chainId, int groupId, String contractAddress, BaseLogResult baseLogResult, Object result) throws Exception;


    /**
     * 加载合约地址
     *
     * @return
     */
    public String loadContractAddress(int chainId, int groupId) {
        ContractTypeEnum contractType = getContractType();

        // load from db
        Optional<ContractDeploy> deployOptional =
                this.contractDeployRepository.findByChainIdAndGroupIdAndContractType(chainId, groupId,contractType.getId());
        ContractDeploy contractDeploy = null;
        if (deployOptional.isPresent()) {
            contractDeploy = deployOptional.get();
            String contractAddress = contractDeploy.getContractAddress();
            if (StringUtils.isNotBlank(contractAddress)) {
                // oracle core already deployed
                contractAddressMap.put(getKey(chainId, groupId), contractAddress);
                return contractAddress;
            }
        }

        contractDeploy = ContractDeploy.build(chainId, groupId, contractType);
        // deploy contract
        String deployedContractAddress = this.deployContract(chainId,groupId);
        if (StringUtils.isNotBlank(deployedContractAddress)) {
            contractDeploy.setContractAddress(deployedContractAddress);
            contractDeployRepository.save(contractDeploy);
            contractAddressMap.put(getKey(chainId, groupId), deployedContractAddress);
        }
        return deployedContractAddress;

    }

    /**
     * get web3j by groupId.
     */
    protected Web3j getWeb3j(int chainId, int groupId) {
        Web3j web3j = web3jMap.get(chainId).get(groupId);
        if (web3j == null) {
            new OracleException(ConstantCode.GROUP_ID_NOT_EXIST);
        }
        return web3j;
    }

    /**
     * @param receipt
     */
    public void dealWithReceipt(TransactionReceipt receipt) {
        log.info("*********" + receipt.getOutput());
        if ("0x16".equals(receipt.getStatus()) && receipt.getOutput().startsWith("0x08c379a0")) {
            log.error("transaction error:[{}]", DecodeOutputUtils.decodeOutputReturnString0x16(receipt.getOutput()));
            throw new OracleException(ConstantCode.SYSTEM_EXCEPTION.getCode(), DecodeOutputUtils.decodeOutputReturnString0x16(receipt.getOutput()));
        }
        if (!"0x0".equals(receipt.getStatus())) {
            log.error("transaction error, status:{} output:{}", receipt.getStatus(), receipt.getOutput());
            throw new OracleException(ConstantCode.SYSTEM_EXCEPTION.getCode(), DecodeOutputUtils.decodeOutputReturnString0x16(receipt.getOutput()));
        }
    }


    /**
     * @param chainId
     * @param groupId
     * @return
     */
    public String getKey(int chainId, int groupId) {
        return String.format("%s_%s", chainId, groupId);
    }

}