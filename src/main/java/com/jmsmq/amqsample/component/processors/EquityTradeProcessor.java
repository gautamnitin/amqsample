package com.jmsmq.amqsample.component.processors;

import com.jmsmq.amqsample.component.DlqPublisher;
import com.jmsmq.amqsample.model.AssetType;
import com.jmsmq.amqsample.model.Trade;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class EquityTradeProcessor extends AbstractTradeProcessor {
    public EquityTradeProcessor(DlqPublisher dlqPublisher, MeterRegistry meterRegistry) {
        super(AssetType.EQUITY, dlqPublisher, meterRegistry);
    }

    @Override
    protected void handleTrade(Trade trade) {
        System.out.println("Processing EQUITY trade: " + trade.getId());
        // Equity-specific logic
    }

}
