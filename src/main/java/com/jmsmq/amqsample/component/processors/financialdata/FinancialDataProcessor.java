package com.jmsmq.amqsample.component.processors.financialdata;

import com.jmsmq.amqsample.model.CustomerFinancialSnapshot;

/**
 * Interface for processing different types of financial data from CustomerFinancialSnapshot
 */
public interface FinancialDataProcessor {
    /**
     * Returns the type of financial data this processor handles
     */
    String getDataType();
    
    /**
     * Process the financial data from the snapshot
     */
    void process(CustomerFinancialSnapshot snapshot);
}