package com.jmsmq.amqsample.component.processors.financialdata;

import com.jmsmq.amqsample.component.DlqPublisher;
import com.jmsmq.amqsample.entity.Transaction;
import com.jmsmq.amqsample.model.CustomerFinancialSnapshot;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Component
public class TransactionProcessor extends AbstractFinancialDataProcessor {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public TransactionProcessor(DlqPublisher dlqPublisher, MeterRegistry meterRegistry) {
        super("transaction", dlqPublisher, meterRegistry);
    }

    @Override
    @Transactional
    protected void processData(CustomerFinancialSnapshot snapshot) {
        List<CustomerFinancialSnapshot.Transaction> transactions = snapshot.getPayload().getTransactions();
        if (transactions == null || transactions.isEmpty()) {
            System.out.println("No transactions to process for customer: " + snapshot.getHeader().getCustomerId());
            return;
        }

        System.out.println("Processing " + transactions.size() + " transactions for customer: " + 
                snapshot.getHeader().getCustomerId());

        for (CustomerFinancialSnapshot.Transaction txn : transactions) {
            Transaction transactionEntity = new Transaction();
            transactionEntity.setTransactionId(txn.getTransactionId());
            transactionEntity.setAccountId(txn.getAccountId());
            transactionEntity.setTransactionDate(txn.getTransactionDate());
            transactionEntity.setAmount(txn.getAmount());
            transactionEntity.setDescription(txn.getDescription());
            transactionEntity.setCategory(txn.getCategory());
            transactionEntity.setMerchant(txn.getMerchant());
            transactionEntity.setType(txn.getType());
            transactionEntity.setStatus(txn.getStatus());

            // Merge to handle both insert and update
            entityManager.merge(transactionEntity);
        }
        
        entityManager.flush();
        System.out.println("Successfully processed transactions for customer: " + snapshot.getHeader().getCustomerId());
    }
}