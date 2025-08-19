package com.jmsmq.amqsample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "accounts")
public class Account {
    
    @Id
    private String accountId;
    
    @Column(nullable = false)
    private String accountName;
    
    private String accountType;
    
    private String institutionName;
    
    private Double balance;
    
    private String currency;
    
    private String status;
    
    private String lastUpdated;
}