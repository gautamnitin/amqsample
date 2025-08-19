package com.jmsmq.amqsample.service;

import com.jmsmq.amqsample.entity.Customer;
import com.jmsmq.amqsample.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Find all customers
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    // Find customer by ID
    public Optional<Customer> findCustomerById(String customerId) {
        return customerRepository.findById(customerId);
    }

    // Find customer by email
    public Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    // Find customers by name containing the given string (case insensitive)
    public List<Customer> findCustomersByNameContaining(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name);
    }

    // Find customers by status
    public List<Customer> findCustomersByStatus(String status) {
        return customerRepository.findByStatus(status);
    }

    // Find customer by phone
    public Optional<Customer> findCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    // Find customers created after a specific date
    public List<Customer> findCustomersCreatedAfter(String createdDate) {
        return customerRepository.findByCreatedDateGreaterThan(createdDate);
    }

    // Find customers updated after a specific date
    public List<Customer> findCustomersUpdatedAfter(String lastUpdated) {
        return customerRepository.findByLastUpdatedGreaterThan(lastUpdated);
    }

    // Save customer
    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Update customer
    @Transactional
    public Customer updateCustomer(Customer customer) {
        if (customerRepository.existsById(customer.getCustomerId())) {
            return customerRepository.save(customer);
        }
        throw new IllegalArgumentException("Customer with ID " + customer.getCustomerId() + " not found");
    }

    // Delete customer by ID
    @Transactional
    public void deleteCustomerById(String customerId) {
        customerRepository.deleteById(customerId);
    }

    // Check if customer exists by ID
    public boolean existsById(String customerId) {
        return customerRepository.existsById(customerId);
    }

    // Count total customers
    public long countCustomers() {
        return customerRepository.count();
    }
}