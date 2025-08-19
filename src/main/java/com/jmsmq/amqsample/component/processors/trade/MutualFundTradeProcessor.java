package com.jmsmq.amqsample.component.processors.trade;

import com.jmsmq.amqsample.component.DlqPublisher;
import com.jmsmq.amqsample.model.AssetType;
import com.jmsmq.amqsample.model.Trade;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MutualFundTradeProcessor extends AbstractTradeProcessor {
    public MutualFundTradeProcessor(DlqPublisher dlqPublisher, MeterRegistry meterRegistry) {
        super(AssetType.MUTUALFUND, dlqPublisher, meterRegistry);
    }

    @Override
    protected void handleTrade(Trade trade) {
        System.out.println("Processing MF trade: " + trade.getId());
        // MF-specific logic
    }
}
