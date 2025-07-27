package com.jmsmq.amqsample.exception;

public class FatalTradeException extends RuntimeException {

    private String tradeId;
    private String errorCode;

    public FatalTradeException(String message) {
        super(message);
    }

    public FatalTradeException(String tradeId, String errorCode, String message) {
        super(message);
        this.tradeId = tradeId;
        this.errorCode = errorCode;
    }

    public String getTradeId() {
        return tradeId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "FatalTradeException{" +
                "tradeId='" + tradeId + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}