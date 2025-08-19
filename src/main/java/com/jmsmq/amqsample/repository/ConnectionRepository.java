package com.jmsmq.amqsample.repository;

import com.jmsmq.amqsample.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, String> {
    
    // Find connections by institution name
    List<Connection> findByInstitutionName(String institutionName);
    
    // Find connections by aggregation type
    List<Connection> findByAggregationType(String aggregationType);
    
    // Find connections by status
    List<Connection> findByStatus(String status);
    
    // Find connections created after a specific date
    List<Connection> findByCreatedDateGreaterThan(String createdDate);
    
    // Find connections by last sync date
    List<Connection> findByLastSyncDate(String lastSyncDate);
}