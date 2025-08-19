package com.jmsmq.amqsample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "transactions")
public class Transaction {
    
    @Id
    private String transactionId;
    
    @Column(nullable = false)
    private String accountId;
    
    private String transactionDate;
    
    private Double amount;
    
    private String description;
    
    private String category;
    
    private String merchant;
    
    private String type;
    
    private String status;
}