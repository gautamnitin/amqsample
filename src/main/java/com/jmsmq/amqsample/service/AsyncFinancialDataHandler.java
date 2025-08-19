package com.jmsmq.amqsample.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmsmq.amqsample.component.processors.financialdata.FinancialDataDispatcher;
import com.jmsmq.amqsample.model.CustomerFinancialSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncFinancialDataHandler {

    @Autowired
    private FinancialDataDispatcher dispatcher;

    @Async("financialDataExecutor")
    public CompletableFuture<Void> processFinancialDataAsync(String message) {
        try {
            CustomerFinancialSnapshot snapshot = new ObjectMapper().readValue(message, CustomerFinancialSnapshot.class);
            dispatcher.dispatchAll(snapshot);
        } catch (Exception e) {
            System.err.println("Error dispatching financial data: " + e.getMessage());
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }
}