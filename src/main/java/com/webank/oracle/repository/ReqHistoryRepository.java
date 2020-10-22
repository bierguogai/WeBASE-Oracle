package com.webank.oracle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webank.oracle.repository.domian.ReqHistory;

/**
 *
 */


public interface ReqHistoryRepository extends JpaRepository<ReqHistory, Long> {

    /**
     *  Find by req_id.
     *
     * @param reqId
     * @return
     */
    Optional<ReqHistory> findByReqId(String reqId);


//    List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);
//
//    // Enables the distinct flag for the query
//    List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
//    List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);
//
//    // Enabling ignoring case for an individual property
//    List<Person> findByLastnameIgnoreCase(String lastname);
//    // Enabling ignoring case for all suitable properties
//    List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);
//
//    // Enabling static ORDER BY for a query
//    List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
//    List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
}