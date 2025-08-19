package com.jmsmq.amqsample.component.processors.financialdata;

import com.jmsmq.amqsample.entity.Connection;
import com.jmsmq.amqsample.model.CustomerFinancialSnapshot;
import org.springframework.stereotype.Component;

/**
 * Transformer for converting CustomerFinancialSnapshot.Connection to Connection entity
 */
@Component
public class ConnectionTransformer implements EntityTransformer<CustomerFinancialSnapshot.Connection, Connection> {

    @Override
    public Connection transformToEntity(CustomerFinancialSnapshot.Connection model) {
        Connection connectionEntity = new Connection();
        connectionEntity.setConnectionId(model.getConnectionId());
        connectionEntity.setInstitutionName(model.getInstitutionName());
        connectionEntity.setAggregationType(model.getAggregationType());
        connectionEntity.setStatus(model.getStatus());
        connectionEntity.setCreatedDate(model.getCreatedDate());
        connectionEntity.setLastSyncDate(model.getLastSyncDate());
        return connectionEntity;
    }
}