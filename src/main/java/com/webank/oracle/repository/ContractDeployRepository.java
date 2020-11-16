package com.webank.oracle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webank.oracle.repository.domian.ContractDeploy;

/**
 *
 */


public interface ContractDeployRepository extends JpaRepository<ContractDeploy, Long> {

    /**
     *
     * @param chainId
     * @param groupId
     * @return
     */
    Optional<ContractDeploy> findByChainIdAndGroupIdAndContractType(int chainId, int groupId,int contractType);
}