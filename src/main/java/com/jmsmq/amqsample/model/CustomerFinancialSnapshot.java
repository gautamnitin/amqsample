package com.jmsmq.amqsample.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerFinancialSnapshot {
    
    @JsonProperty("header")
    private Header header;
    
    @JsonProperty("payload")
    private Payload payload;
    
    @Data
    public static class Header {
        @JsonProperty("messageId")
        private String messageId;
        
        @JsonProperty("timestamp")
        private String timestamp;
        
        @JsonProperty("eventType")
        private String eventType;
        
        @JsonProperty("source")
        private String source;
        
        @JsonProperty("customerId")
        private String customerId;
    }
    
    @Data
    public static class Payload {
        @JsonProperty("connections")
        private List<Connection> connections;
        
        @JsonProperty("accounts")
        private List<Account> accounts;
        
        @JsonProperty("transactions")
        private List<Transaction> transactions;
    }
    
    @Data
    public static class Connection {
        @JsonProperty("connectionId")
        private String connectionId;
        
        @JsonProperty("institutionName")
        private String institutionName;
        
        @JsonProperty("aggregationType")
        private String aggregationType;
        
        @JsonProperty("status")
        private String status;
        
        @JsonProperty("createdDate")
        private String createdDate;
        
        @JsonProperty("lastSyncDate")
        private String lastSyncDate;
    }
    
    @Data
    public static class Account {
        @JsonProperty("accountId")
        private String accountId;
        
        @JsonProperty("accountName")
        private String accountName;
        
        @JsonProperty("accountType")
        private String accountType;
        
        @JsonProperty("institutionName")
        private String institutionName;
        
        @JsonProperty("balance")
        private Double balance;
        
        @JsonProperty("currency")
        private String currency;
        
        @JsonProperty("status")
        private String status;
        
        @JsonProperty("lastUpdated")
        private String lastUpdated;
    }
    
    @Data
    public static class Transaction {
        @JsonProperty("transactionId")
        private String transactionId;
        
        @JsonProperty("accountId")
        private String accountId;
        
        @JsonProperty("transactionDate")
        private String transactionDate;
        
        @JsonProperty("amount")
        private Double amount;
        
        @JsonProperty("description")
        private String description;
        
        @JsonProperty("category")
        private String category;
        
        @JsonProperty("merchant")
        private String merchant;
        
        @JsonProperty("type")
        private String type;
        
        @JsonProperty("status")
        private String status;
    }
}