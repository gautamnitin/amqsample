package com.jmsmq.amqsample.performance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmsmq.amqsample.component.processors.financialdata.AccountProcessor;
import com.jmsmq.amqsample.model.CustomerFinancialSnapshot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Performance test for AccountProcessor
 * This test simulates processing a large number of accounts to verify performance improvements
 */
@SpringBootTest
@ActiveProfiles("test")
public class AccountProcessorPerformanceTest {

    @Autowired
    private AccountProcessor accountProcessor;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Test processing a large batch of accounts
     * This test creates a snapshot with a large number of accounts and measures processing time
     */
    @Test
    public void testLargeBatchProcessing() throws IOException {
        int count = 1059;
        // Load sample snapshot from JSON file
        InputStream is = getClass().getClassLoader().getResourceAsStream("sample_json/CustomerFinancialSnapshot.json");
        CustomerFinancialSnapshot baseSnapshot = objectMapper.readValue(is, CustomerFinancialSnapshot.class);
        
        // Create a large snapshot with many accounts
        CustomerFinancialSnapshot largeSnapshot = createLargeSnapshot(baseSnapshot, count);
        
        // Measure processing time
        long startTime = System.currentTimeMillis();
        
        // Process the snapshot
        accountProcessor.process(largeSnapshot);
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("[DEBUG_LOG] Processed " +count+ " accounts in " + duration + " ms" + " actual count: " + accountProcessor.getCount());
        System.out.println("[DEBUG_LOG] Average time per account: " + (duration / 1000.0) + " ms");
    }
    
    /**
     * Creates a large snapshot with the specified number of accounts
     * @param baseSnapshot The base snapshot to clone
     * @param accountCount The number of accounts to include
     * @return A new snapshot with many accounts
     */
    private CustomerFinancialSnapshot createLargeSnapshot(CustomerFinancialSnapshot baseSnapshot, int accountCount) {
        CustomerFinancialSnapshot snapshot = new CustomerFinancialSnapshot();
        
        // Copy header
        CustomerFinancialSnapshot.Header header = new CustomerFinancialSnapshot.Header();
        header.setMessageId(UUID.randomUUID().toString());
        header.setTimestamp(baseSnapshot.getHeader().getTimestamp());
        header.setEventType(baseSnapshot.getHeader().getEventType());
        header.setSource(baseSnapshot.getHeader().getSource());
        header.setCustomerId(baseSnapshot.getHeader().getCustomerId());
        snapshot.setHeader(header);
        
        // Create payload with many accounts
        CustomerFinancialSnapshot.Payload payload = new CustomerFinancialSnapshot.Payload();
        List<CustomerFinancialSnapshot.Account> accounts = new ArrayList<>(accountCount);
        
        // Use the first account as a template
        CustomerFinancialSnapshot.Account templateAccount = 
                baseSnapshot.getPayload().getAccounts().get(0);
        
        // Create many accounts
        for (int i = 0; i < accountCount; i++) {
            CustomerFinancialSnapshot.Account account = new CustomerFinancialSnapshot.Account();
            account.setAccountId("ACC" + UUID.randomUUID().toString().substring(0, 8));
            account.setAccountName("Test Account " + i);
            account.setAccountType(templateAccount.getAccountType());
            account.setInstitutionName(templateAccount.getInstitutionName());
            account.setBalance(Math.random() * 10000);
            account.setCurrency(templateAccount.getCurrency());
            account.setStatus("ACTIVE");
            account.setLastUpdated(templateAccount.getLastUpdated());
            
            accounts.add(account);
        }
        
        payload.setAccounts(accounts);
        snapshot.setPayload(payload);
        
        return snapshot;
    }
}