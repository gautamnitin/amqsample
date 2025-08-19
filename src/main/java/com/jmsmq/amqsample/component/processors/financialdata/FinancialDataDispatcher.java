package com.jmsmq.amqsample.component.processors.financialdata;

import com.jmsmq.amqsample.model.CustomerFinancialSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Dispatches CustomerFinancialSnapshot data to appropriate processors
 */
@Component
public class FinancialDataDispatcher {

    private final ConnectionProcessor connectionProcessor;
    private final AccountProcessor accountProcessor;
    private final TransactionProcessor transactionProcessor;

    @Autowired
    public FinancialDataDispatcher(
            ConnectionProcessor connectionProcessor,
            AccountProcessor accountProcessor,
            TransactionProcessor transactionProcessor) {
        this.connectionProcessor = connectionProcessor;
        this.accountProcessor = accountProcessor;
        this.transactionProcessor = transactionProcessor;
    }

    /**
     * Dispatches the snapshot data to all processors in parallel
     */
    public void dispatchAll(CustomerFinancialSnapshot snapshot) {
        CompletableFuture<Void> connectionFuture = processConnectionsAsync(snapshot);
        CompletableFuture<Void> accountFuture = processAccountsAsync(snapshot);
        CompletableFuture<Void> transactionFuture = processTransactionsAsync(snapshot);
        
        // Wait for all processing to complete
        CompletableFuture.allOf(connectionFuture, accountFuture, transactionFuture).join();
    }
    
    @Async("financialDataExecutor")
    public CompletableFuture<Void> processConnectionsAsync(CustomerFinancialSnapshot snapshot) {
        connectionProcessor.process(snapshot);
        return CompletableFuture.completedFuture(null);
    }
    
    @Async("financialDataExecutor")
    public CompletableFuture<Void> processAccountsAsync(CustomerFinancialSnapshot snapshot) {
        accountProcessor.process(snapshot);
        return CompletableFuture.completedFuture(null);
    }
    
    @Async("financialDataExecutor")
    public CompletableFuture<Void> processTransactionsAsync(CustomerFinancialSnapshot snapshot) {
        transactionProcessor.process(snapshot);
        return CompletableFuture.completedFuture(null);
    }
}