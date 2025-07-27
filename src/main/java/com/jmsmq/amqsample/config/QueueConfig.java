package com.jmsmq.amqsample.config;

import jakarta.jms.Queue;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    public static final String TRADE_QUEUE = "trade.queue";

    @Bean
    public Queue queue() {
        return new ActiveMQQueue(TRADE_QUEUE);
    }

}
