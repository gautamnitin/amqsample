package com.jmsmq.amqsample.component.processors.trade;

import com.jmsmq.amqsample.model.AssetType;
import com.jmsmq.amqsample.model.Trade;

public interface TradeProcessor {
    AssetType getAssetType();
    void process(Trade trade);
}
