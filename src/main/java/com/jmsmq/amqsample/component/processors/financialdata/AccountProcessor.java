package com.jmsmq.amqsample.component.processors.financialdata;

import com.jmsmq.amqsample.component.DlqPublisher;
import com.jmsmq.amqsample.entity.Account;
import com.jmsmq.amqsample.model.CustomerFinancialSnapshot;
import com.jmsmq.amqsample.service.AccountService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountProcessor extends AbstractGenericFinancialDataProcessor<CustomerFinancialSnapshot.Account, Account> {

    @PersistenceContext
    private EntityManager entityManager;
    
    private final AccountService accountService;

    @Autowired
    public AccountProcessor(
            DlqPublisher dlqPublisher, 
            MeterRegistry meterRegistry, 
            AccountService accountService,
            AccountTransformer accountTransformer) {
        super("account", dlqPublisher, meterRegistry, accountTransformer);
        this.accountService = accountService;
    }

    @Override
    protected List<CustomerFinancialSnapshot.Account> extractModelList(CustomerFinancialSnapshot snapshot) {
        return snapshot.getPayload().getAccounts();
    }

    @Override
    @Transactional
    protected void processData(CustomerFinancialSnapshot snapshot) {
        List<CustomerFinancialSnapshot.Account> accounts = extractModelList(snapshot);
        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No accounts to process for customer: " + snapshot.getHeader().getCustomerId());
            return;
        }

        System.out.println("Processing " + accounts.size() + " accounts for customer: " + 
                snapshot.getHeader().getCustomerId());

        // Process in batches of 100 for better performance
        final int batchSize = 100;
        
        // Use a single parallel stream to process accounts
        accounts.parallelStream()
            .map(getTransformer()::transformToEntity)
            .collect(java.util.stream.Collectors.groupingBy(
                account -> Math.floorMod(account.getAccountId().hashCode(), (int)Math.ceil((double)accounts.size() / batchSize)),
                java.util.stream.Collectors.toList()
            ))
            .values()
            .forEach(this::saveAccountBatch);
        
        System.out.println("Successfully processed accounts for customer: " + snapshot.getHeader().getCustomerId());
    }
    
    /**
     * Save a batch of accounts using JPA batch processing
     * Handles both new and update account records
     */
    private void saveAccountBatch(List<Account> batch) {
        batch.forEach(account -> {
            if (accountService.existsById(account.getAccountId())) {
                // Update existing account
                Account existingAccount = accountService.findAccountById(account.getAccountId())
                    .orElseThrow(() -> new IllegalStateException("Account not found despite existsById returning true"));
                
                // Update fields
                existingAccount.setAccountName(account.getAccountName());
                existingAccount.setAccountType(account.getAccountType());
                existingAccount.setInstitutionName(account.getInstitutionName());
                existingAccount.setBalance(account.getBalance());
                existingAccount.setCurrency(account.getCurrency());
                existingAccount.setStatus(account.getStatus());
                existingAccount.setLastUpdated(account.getLastUpdated());
                
                accountService.saveAccount(existingAccount);
            } else {
                // Create new account
                accountService.saveAccount(account);
            }
        });
        
        // Force flush to ensure all entities are written to the database
        entityManager.flush();
        entityManager.clear();
    }
}