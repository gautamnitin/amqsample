package com.jmsmq.amqsample.component.processors.financialdata;

import com.jmsmq.amqsample.component.DlqPublisher;
import com.jmsmq.amqsample.entity.Connection;
import com.jmsmq.amqsample.model.CustomerFinancialSnapshot;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Component
public class ConnectionProcessor extends AbstractGenericFinancialDataProcessor<CustomerFinancialSnapshot.Connection, Connection> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ConnectionProcessor(
            DlqPublisher dlqPublisher, 
            MeterRegistry meterRegistry,
            ConnectionTransformer connectionTransformer) {
        super("connection", dlqPublisher, meterRegistry, connectionTransformer);
    }
    
    @Override
    protected List<CustomerFinancialSnapshot.Connection> extractModelList(CustomerFinancialSnapshot snapshot) {
        return snapshot.getPayload().getConnections();
    }

    @Override
    @Transactional
    protected void processData(CustomerFinancialSnapshot snapshot) {
        List<CustomerFinancialSnapshot.Connection> connections = extractModelList(snapshot);
        if (connections == null || connections.isEmpty()) {
            System.out.println("No connections to process for customer: " + snapshot.getHeader().getCustomerId());
            return;
        }

        System.out.println("Processing " + connections.size() + " connections for customer: " + 
                snapshot.getHeader().getCustomerId());

        // Transform connections to entities and persist in batches using a single stream
        final int batchSize = 100;
        com.jmsmq.amqsample.util.BatchingUtils.mapAndBatch(
                connections.stream(),
                getTransformer()::transformToEntity,
                batchSize,
                batch -> {
                    batch.forEach(entityManager::merge);
                    entityManager.flush();
                    entityManager.clear();
                }
        );
        
        System.out.println("Successfully processed connections for customer: " + snapshot.getHeader().getCustomerId());
    }
}