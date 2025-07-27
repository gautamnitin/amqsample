package com.jmsmq.amqsample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.jmsmq.amqsample.config.QueueConfig.TRADE_QUEUE;

@Service
public class TradeMessageConsumer {
    @Autowired
    private AsyncTradeHandler tradeHandler;

    @JmsListener(destination = TRADE_QUEUE)
    public void onMessage(String message) {
        tradeHandler.processTradeAsync(message);
    }
}
