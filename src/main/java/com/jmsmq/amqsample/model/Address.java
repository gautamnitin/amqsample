package com.jmsmq.amqsample.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Address {
    @Id
    private Long id;
    private String postalCode;
    private String city;
    private String region;
    // other fields
}


