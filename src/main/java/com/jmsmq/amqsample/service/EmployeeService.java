package com.jmsmq.amqsample.service;

import com.jmsmq.amqsample.model.Employee;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.jmsmq.amqsample.model.CacheNames.EMPLOYEE;

@Service
public class EmployeeService {

    //@Autowired
    //private EmployeeRepository employeeRepository;

    @Cacheable(value = EMPLOYEE, key = "#name")
    public Optional<Employee> getEmployeeByName(String name) {
        //return employeeRepository.findByName(name);
        return Optional.of(new Employee());
    }

    @CacheEvict(value = EMPLOYEE, key = "#name")
    public void evictEmployeeCache(String name) {
        // triggered on update/delete
    }
    //Another example of composit key
    @Cacheable(value = "employeeCache", key = "#departmentId + ':' + #employeeName")
    public Optional<Employee> getEmployee(String departmentId, String employeeName) {
        //return employeeService.getEmployee(departmentId, employeeName);
        return Optional.of(new Employee());
    }
}