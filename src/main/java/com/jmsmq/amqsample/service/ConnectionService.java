package com.jmsmq.amqsample.service;

import com.jmsmq.amqsample.entity.Connection;
import com.jmsmq.amqsample.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ConnectionService {

    private final ConnectionRepository connectionRepository;

    @Autowired
    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    // Find all connections
    public List<Connection> findAllConnections() {
        return connectionRepository.findAll();
    }

    // Find connection by ID
    public Optional<Connection> findConnectionById(String connectionId) {
        return connectionRepository.findById(connectionId);
    }

    // Find connections by institution name
    public List<Connection> findConnectionsByInstitutionName(String institutionName) {
        return connectionRepository.findByInstitutionName(institutionName);
    }

    // Find connections by aggregation type
    public List<Connection> findConnectionsByAggregationType(String aggregationType) {
        return connectionRepository.findByAggregationType(aggregationType);
    }

    // Find connections by status
    public List<Connection> findConnectionsByStatus(String status) {
        return connectionRepository.findByStatus(status);
    }

    // Find connections created after a specific date
    public List<Connection> findConnectionsCreatedAfter(String createdDate) {
        return connectionRepository.findByCreatedDateGreaterThan(createdDate);
    }

    // Find connections by last sync date
    public List<Connection> findConnectionsByLastSyncDate(String lastSyncDate) {
        return connectionRepository.findByLastSyncDate(lastSyncDate);
    }

    // Save connection
    @Transactional
    public Connection saveConnection(Connection connection) {
        return connectionRepository.save(connection);
    }

    // Update connection
    @Transactional
    public Connection updateConnection(Connection connection) {
        if (connectionRepository.existsById(connection.getConnectionId())) {
            return connectionRepository.save(connection);
        }
        throw new IllegalArgumentException("Connection with ID " + connection.getConnectionId() + " not found");
    }

    // Delete connection by ID
    @Transactional
    public void deleteConnectionById(String connectionId) {
        connectionRepository.deleteById(connectionId);
    }

    // Check if connection exists by ID
    public boolean existsById(String connectionId) {
        return connectionRepository.existsById(connectionId);
    }

    // Count total connections
    public long countConnections() {
        return connectionRepository.count();
    }
}