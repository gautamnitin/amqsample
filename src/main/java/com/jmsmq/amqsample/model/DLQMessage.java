package com.jmsmq.amqsample.model;


import java.time.LocalDateTime;

public class DLQMessage {

    private String messageId;
    private String messageType;
    private String customerId;
    private double amount;
    private String error;
    private LocalDateTime timestamp;

    public DLQMessage() { }

    public DLQMessage(Trade trade, String errorMessage) {
        this.messageId = trade.getId();
        this.messageType = trade.getType();
        this.amount = trade.getAmount();
        this.error = errorMessage;
        this.timestamp = LocalDateTime.now();
    }
    
    public DLQMessage(CustomerFinancialSnapshot snapshot, String errorMessage) {
        this.messageId = snapshot.getHeader().getMessageId();
        this.messageType = snapshot.getHeader().getEventType();
        this.customerId = snapshot.getHeader().getCustomerId();
        this.amount = 0.0; // No direct amount in snapshot
        this.error = errorMessage;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "DLQMessage{" +
                "messageId='" + messageId + '\'' +
                ", messageType='" + messageType + '\'' +
                ", customerId='" + customerId + '\'' +
                ", amount=" + amount +
                ", error='" + error + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}