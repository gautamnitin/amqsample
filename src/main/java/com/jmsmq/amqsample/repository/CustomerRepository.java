package com.jmsmq.amqsample.repository;

import com.jmsmq.amqsample.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    
    // Find customer by email
    Optional<Customer> findByEmail(String email);
    
    // Find customers by name containing the given string (case insensitive)
    List<Customer> findByNameContainingIgnoreCase(String name);
    
    // Find customers by status
    List<Customer> findByStatus(String status);
    
    // Find customers by phone
    Optional<Customer> findByPhone(String phone);
    
    // Find customers created after a specific date
    List<Customer> findByCreatedDateGreaterThan(String createdDate);
    
    // Find customers updated after a specific date
    List<Customer> findByLastUpdatedGreaterThan(String lastUpdated);
}