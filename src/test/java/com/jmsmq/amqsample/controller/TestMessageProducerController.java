package com.jmsmq.amqsample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmsmq.amqsample.model.Trade;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Test version of MessageProducerController that doesn't use Queue beans
 * This is used only for tests to avoid JMS-related issues
 */
@Component
@Primary
public class TestMessageProducerController {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public TestMessageProducerController(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<String> sendMessage(@RequestBody Trade message) {
        // In test mode, we don't actually send messages
        return ResponseEntity.ok("Test mode - Message would be sent: " + message);
    }

    public ResponseEntity<String> sendMessages(@RequestBody List<Trade> messages) {
        // In test mode, we don't actually send messages
        return ResponseEntity.ok("Test mode - Messages would be sent: " + messages);
    }
}