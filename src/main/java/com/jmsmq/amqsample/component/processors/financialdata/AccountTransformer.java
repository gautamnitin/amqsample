package com.jmsmq.amqsample.component.processors.financialdata;

import com.jmsmq.amqsample.entity.Account;
import com.jmsmq.amqsample.model.CustomerFinancialSnapshot;
import org.springframework.stereotype.Component;

/**
 * Transformer for converting CustomerFinancialSnapshot.Account to Account entity
 */
@Component
public class AccountTransformer implements EntityTransformer<CustomerFinancialSnapshot.Account, Account> {

    @Override
    public Account transformToEntity(CustomerFinancialSnapshot.Account model) {
        Account accountEntity = new Account();
        accountEntity.setAccountId(model.getAccountId());
        accountEntity.setAccountName(model.getAccountName());
        accountEntity.setAccountType(model.getAccountType());
        accountEntity.setInstitutionName(model.getInstitutionName());
        accountEntity.setBalance(model.getBalance());
        accountEntity.setCurrency(model.getCurrency());
        accountEntity.setStatus(model.getStatus());
        accountEntity.setLastUpdated(model.getLastUpdated());
        return accountEntity;
    }
}