package com.jmsmq.amqsample.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmsmq.amqsample.component.processors.trade.TradeDispatcher;
import com.jmsmq.amqsample.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncTradeHandler {

    @Autowired
    private TradeDispatcher dispatcher;

    @Async("tradeExecutor")
    public CompletableFuture<Void> processTradeAsync(String message) {
        try {
            Trade trade = new ObjectMapper().readValue(message, Trade.class);
            dispatcher.dispatch(trade);
        } catch (Exception e) {
            System.err.println("Error dispatching trade: " + e.getMessage());
        }
        return CompletableFuture.completedFuture(null);
    }
}