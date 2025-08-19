package com.jmsmq.amqsample.repository;

import com.jmsmq.amqsample.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    
    // Find transactions by account ID
    List<Transaction> findByAccountId(String accountId);
    
    // Find transactions by type
    List<Transaction> findByType(String type);
    
    // Find transactions by category
    List<Transaction> findByCategory(String category);
    
    // Find transactions by merchant
    List<Transaction> findByMerchant(String merchant);
    
    // Find transactions by status
    List<Transaction> findByStatus(String status);
    
    // Find transactions with amount greater than specified value
    List<Transaction> findByAmountGreaterThan(Double amount);
    
    // Find transactions with amount less than specified value
    List<Transaction> findByAmountLessThan(Double amount);
    
    // Find transactions after a specific date
    List<Transaction> findByTransactionDateGreaterThan(String transactionDate);
    
    // Find transactions containing description (case insensitive)
    List<Transaction> findByDescriptionContainingIgnoreCase(String description);
    
    // Custom query to find sum of transactions by account ID
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.accountId = ?1")
    Double sumAmountByAccountId(String accountId);
}