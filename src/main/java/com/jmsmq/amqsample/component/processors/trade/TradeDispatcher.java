package com.jmsmq.amqsample.component.processors.trade;

import com.jmsmq.amqsample.model.AssetType;
import com.jmsmq.amqsample.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TradeDispatcher {

    private final Map<AssetType, TradeProcessor> processorMap;

    @Autowired
    public TradeDispatcher(List<TradeProcessor> tradeProcessors) {
        processorMap = tradeProcessors.stream()
                .collect(Collectors.toMap(
                        TradeProcessor::getAssetType,
                        Function.identity()
                ));
    }

    public void dispatch(Trade trade) {
        AssetType selectedAssetType = AssetType.valueOf(trade.getType());
        TradeProcessor processor = processorMap.get(selectedAssetType);
        if (processor != null) {
            processor.process(trade);
        } else {
            System.err.println("No processor found for trade type: " + trade.getType());
        }
    }



}
