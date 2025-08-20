package com.jmsmq.amqsample.component.processors.financialdata;

import com.jmsmq.amqsample.component.DlqPublisher;
import com.jmsmq.amqsample.model.CustomerFinancialSnapshot;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract base class for financial data processors that implements common functionality
 */
public abstract class AbstractFinancialDataProcessor implements FinancialDataProcessor {

    private final String dataType;
    private final DlqPublisher dlqPublisher;
    private final MeterRegistry meterRegistry;

    public AbstractFinancialDataProcessor(String dataType, DlqPublisher dlqPublisher, MeterRegistry meterRegistry) {
        this.dataType = dataType;
        this.dlqPublisher = dlqPublisher;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public String getDataType() {
        return this.dataType;
    }

    @Override
    @Transactional
    public void process(CustomerFinancialSnapshot snapshot) {
        // Start metrics timer
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            logStart(snapshot);
            processData(snapshot); // Subclass implementation
            logEnd(snapshot);

            meterRegistry.counter("financial.data.process.success", "type", dataType).increment();
        } catch (Exception e) {
            meterRegistry.counter("financial.data.process.error", "type", dataType).increment();
            logError(snapshot, e);
            dlqPublisher.publish(snapshot, e.getMessage()); // Push to DLQ
        } finally {
            sample.stop(meterRegistry.timer("financial.data.process.duration", "type", dataType));
        }
    }

    /**
     * Subclasses must implement this method to process specific financial data
     */
    protected abstract void processData(CustomerFinancialSnapshot snapshot);

    private void logStart(CustomerFinancialSnapshot snapshot) {
        System.out.println(Thread.currentThread().getName() + "=>Started processing " + dataType + 
                " data for customer: " + snapshot.getHeader().getCustomerId());
    }

    private void logEnd(CustomerFinancialSnapshot snapshot) {
        System.out.println("Completed processing " + dataType + 
                " data for customer: " + snapshot.getHeader().getCustomerId());
    }

    private void logError(CustomerFinancialSnapshot snapshot, Exception e) {
        System.err.println("Error processing " + dataType + 
                " data for customer: " + snapshot.getHeader().getCustomerId() + 
                " | " + e.getMessage());
    }
}