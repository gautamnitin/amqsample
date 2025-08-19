package com.jmsmq.amqsample;

import jakarta.jms.Queue;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableAutoConfiguration(exclude = {
    JmsAutoConfiguration.class,
    ArtemisAutoConfiguration.class
})
public class TestConfig {
    
    @Bean
    @Primary
    public Queue tradeQueue() {
        return Mockito.mock(Queue.class);
    }
    
    @Bean
    public JmsTemplate jmsTemplate() {
        return Mockito.mock(JmsTemplate.class);
    }
}