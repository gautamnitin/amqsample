package com.jmsmq.amqsample.config;

import jakarta.jms.Queue;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    public static final String TRADE_QUEUE = "trade.queue";
    public static final String FINANCIAL_SNAPSHOT_QUEUE = "financial.snapshot.queue";

    @Bean
    public Queue tradeQueue() {
        return new ActiveMQQueue(TRADE_QUEUE);
    }
    
    @Bean
    public Queue financialSnapshotQueue() {
        return new ActiveMQQueue(FINANCIAL_SNAPSHOT_QUEUE);
    }

}
