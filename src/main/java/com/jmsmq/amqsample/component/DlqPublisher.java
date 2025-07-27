package com.jmsmq.amqsample.component;

import com.jmsmq.amqsample.model.DLQMessage;
import com.jmsmq.amqsample.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class DlqPublisher {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void publish(Trade trade, String errorMsg) {
        DLQMessage message = new DLQMessage(trade, errorMsg);
        jmsTemplate.convertAndSend("dlq.queue", message);
    }
}