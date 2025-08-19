package com.jmsmq.amqsample.repository;

import com.jmsmq.amqsample.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    
    // Find accounts by institution name
    List<Account> findByInstitutionName(String institutionName);
    
    // Find accounts by account type
    List<Account> findByAccountType(String accountType);
    
    // Find accounts by status
    List<Account> findByStatus(String status);
    
    // Find accounts with balance greater than specified amount
    List<Account> findByBalanceGreaterThan(Double balance);
    
    // Find accounts with balance less than specified amount
    List<Account> findByBalanceLessThan(Double balance);
    
    // Find accounts by currency
    List<Account> findByCurrency(String currency);
}