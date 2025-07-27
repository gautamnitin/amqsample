package com.jmsmq.amqsample.component.processors;

import com.jmsmq.amqsample.component.DlqPublisher;
import com.jmsmq.amqsample.exception.FatalTradeException;
import com.jmsmq.amqsample.model.AssetType;
import com.jmsmq.amqsample.model.Trade;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

public abstract class AbstractTradeProcessor implements TradeProcessor {


    private final DlqPublisher dlqPublisher; // Handles DLQ logic
    private final MeterRegistry meterRegistry;
    private final AssetType assetType;

    public AbstractTradeProcessor(AssetType assetType, DlqPublisher dlqPublisher, MeterRegistry meterRegistry) {
        this.dlqPublisher = dlqPublisher;
        this.meterRegistry = meterRegistry;
        this.assetType = assetType;
    }

    @Override
    public AssetType getAssetType() {
        return this.assetType;
    }

    @Override
    public void process(Trade trade) {
        // Meter
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            logStart(trade);
            handleTrade(trade); // Subclass implementation
            logEnd(trade);

            meterRegistry.counter("trade.process.success", "type", trade.getType()).increment();
        } catch (FatalTradeException ex) {
            logError(trade, ex);
            meterRegistry.counter("trade.process.fatal", "type", trade.getType()).increment();
            dlqPublisher.publish(trade, ex.getMessage()); // Push to DLQ
        } catch (Exception e) {
            meterRegistry.counter("trade.process.error", "type", trade.getType()).increment();
            logError(trade, e);
            // Non-fatal exception: could log, alert, or retry
        } finally {
            sample.stop(meterRegistry.timer("trade.process.duration", "type", trade.getType()));
        }
    }

    protected abstract void handleTrade(Trade trade); // force subclasses to implement domain logic

    private void logStart(Trade trade) {
        System.out.println(Thread.currentThread().getName() + "=>Started processing " + trade.getType() + " trade: " + trade.getId());
    }

    private void logEnd(Trade trade) {
        System.out.println("Completed processing " + trade.getType() + " trade: " + trade.getId());
    }

    private void logError(Trade trade, Exception e) {
        System.err.println("Error processing trade: " + trade.getId() + " | " + e.getMessage());
    }
}