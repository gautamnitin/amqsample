package com.jmsmq.amqsample.service;

import com.jmsmq.amqsample.entity.Transaction;
import com.jmsmq.amqsample.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Find all transactions
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    // Find transaction by ID
    public Optional<Transaction> findTransactionById(String transactionId) {
        return transactionRepository.findById(transactionId);
    }

    // Find transactions by account ID
    public List<Transaction> findTransactionsByAccountId(String accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    // Find transactions by type
    public List<Transaction> findTransactionsByType(String type) {
        return transactionRepository.findByType(type);
    }

    // Find transactions by category
    public List<Transaction> findTransactionsByCategory(String category) {
        return transactionRepository.findByCategory(category);
    }

    // Find transactions by merchant
    public List<Transaction> findTransactionsByMerchant(String merchant) {
        return transactionRepository.findByMerchant(merchant);
    }

    // Find transactions by status
    public List<Transaction> findTransactionsByStatus(String status) {
        return transactionRepository.findByStatus(status);
    }

    // Find transactions with amount greater than specified value
    public List<Transaction> findTransactionsWithAmountGreaterThan(Double amount) {
        return transactionRepository.findByAmountGreaterThan(amount);
    }

    // Find transactions with amount less than specified value
    public List<Transaction> findTransactionsWithAmountLessThan(Double amount) {
        return transactionRepository.findByAmountLessThan(amount);
    }

    // Find transactions after a specific date
    public List<Transaction> findTransactionsAfterDate(String transactionDate) {
        return transactionRepository.findByTransactionDateGreaterThan(transactionDate);
    }

    // Find transactions containing description (case insensitive)
    public List<Transaction> findTransactionsByDescriptionContaining(String description) {
        return transactionRepository.findByDescriptionContainingIgnoreCase(description);
    }

    // Get sum of transactions by account ID
    public Double getSumOfTransactionsByAccountId(String accountId) {
        return transactionRepository.sumAmountByAccountId(accountId);
    }

    // Save transaction
    @Transactional
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Update transaction
    @Transactional
    public Transaction updateTransaction(Transaction transaction) {
        if (transactionRepository.existsById(transaction.getTransactionId())) {
            return transactionRepository.save(transaction);
        }
        throw new IllegalArgumentException("Transaction with ID " + transaction.getTransactionId() + " not found");
    }

    // Delete transaction by ID
    @Transactional
    public void deleteTransactionById(String transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    // Check if transaction exists by ID
    public boolean existsById(String transactionId) {
        return transactionRepository.existsById(transactionId);
    }

    // Count total transactions
    public long countTransactions() {
        return transactionRepository.count();
    }
}