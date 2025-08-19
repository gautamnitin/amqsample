package com.jmsmq.amqsample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "connections")
public class Connection {
    
    @Id
    private String connectionId;
    
    @Column(nullable = false)
    private String institutionName;
    
    private String aggregationType;
    
    private String status;
    
    private String createdDate;
    
    private String lastSyncDate;
}