package com.jmsmq.amqsample.component.processors.trade;

import com.jmsmq.amqsample.component.DlqPublisher;
import com.jmsmq.amqsample.model.AssetType;
import com.jmsmq.amqsample.model.Trade;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class BondTradeProcessor extends AbstractTradeProcessor {


    public BondTradeProcessor(DlqPublisher dlqPublisher, MeterRegistry meterRegistry) {
        super(AssetType.BOND, dlqPublisher, meterRegistry);
    }

    @Override
    protected void handleTrade(Trade trade) {
        System.out.println("Processing BOND trade: " + trade.getId());
        // Bond-specific logic
    }
}
