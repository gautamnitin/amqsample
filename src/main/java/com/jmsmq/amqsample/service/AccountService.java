package com.jmsmq.amqsample.service;

import com.jmsmq.amqsample.entity.Account;
import com.jmsmq.amqsample.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Find all accounts
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    // Find account by ID
    @Cacheable(value = "accountCache", key = "#accountId")
    public Optional<Account> findAccountById(String accountId) {
        return accountRepository.findById(accountId);
    }

    // Find accounts by institution name
    public List<Account> findAccountsByInstitutionName(String institutionName) {
        return accountRepository.findByInstitutionName(institutionName);
    }

    // Find accounts by account type
    public List<Account> findAccountsByAccountType(String accountType) {
        return accountRepository.findByAccountType(accountType);
    }

    // Find accounts by status
    public List<Account> findAccountsByStatus(String status) {
        return accountRepository.findByStatus(status);
    }

    // Find accounts with balance greater than specified amount
    public List<Account> findAccountsWithBalanceGreaterThan(Double balance) {
        return accountRepository.findByBalanceGreaterThan(balance);
    }

    // Find accounts with balance less than specified amount
    public List<Account> findAccountsWithBalanceLessThan(Double balance) {
        return accountRepository.findByBalanceLessThan(balance);
    }

    // Find accounts by currency
    public List<Account> findAccountsByCurrency(String currency) {
        return accountRepository.findByCurrency(currency);
    }

    // Save account
    @Transactional
    @CacheEvict(value = "accountCache", key = "#account.accountId")
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    // Update account
    @Transactional
    @CacheEvict(value = "accountCache", key = "#account.accountId")
    public Account updateAccount(Account account) {
        if (accountRepository.existsById(account.getAccountId())) {
            return accountRepository.save(account);
        }
        throw new IllegalArgumentException("Account with ID " + account.getAccountId() + " not found");
    }

    // Delete account by ID
    @Transactional
    @CacheEvict(value = "accountCache", key = "#accountId")
    public void deleteAccountById(String accountId) {
        accountRepository.deleteById(accountId);
    }

    // Check if account exists by ID
    public boolean existsById(String accountId) {
        return accountRepository.existsById(accountId);
    }

    // Count total accounts
    public long countAccounts() {
        return accountRepository.count();
    }
}