package com.jmsmq.amqsample.model;


import java.time.LocalDateTime;

public class DLQMessage {

    private String tradeId;
    private String tradeType;
    private double amount;
    private String error;
    private LocalDateTime timestamp;

    public DLQMessage() { }

    public DLQMessage(Trade trade, String errorMessage) {
        this.tradeId = trade.getId();
        this.tradeType = trade.getType();
        this.amount = trade.getAmount();
        this.error = errorMessage;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
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
                "tradeId='" + tradeId + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", amount=" + amount +
                ", error='" + error + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}