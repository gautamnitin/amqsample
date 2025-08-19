package com.jmsmq.amqsample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import static com.jmsmq.amqsample.config.QueueConfig.FINANCIAL_SNAPSHOT_QUEUE;

/**
 * Consumer for CustomerFinancialSnapshot messages
 * Listens to the financial snapshot queue and delegates processing to AsyncFinancialDataHandler
 */
@Service
public class CustomerFinancialSnapshotConsumer {
    
    @Autowired
    private AsyncFinancialDataHandler financialDataHandler;

    @JmsListener(destination = FINANCIAL_SNAPSHOT_QUEUE)
    public void onMessage(String message) {
        System.out.println("Received financial snapshot message");
        financialDataHandler.processFinancialDataAsync(message);
    }
}