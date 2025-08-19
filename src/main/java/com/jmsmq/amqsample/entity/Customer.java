package com.jmsmq.amqsample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "customers")
public class Customer {
    
    @Id
    private String customerId;
    
    private String name;
    
    private String email;
    
    private String phone;
    
    private String address;
    
    private String createdDate;
    
    private String lastUpdated;
    
    private String status;
}