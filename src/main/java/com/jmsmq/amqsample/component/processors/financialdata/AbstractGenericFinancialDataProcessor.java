package com.jmsmq.amqsample.component.processors.financialdata;

import com.jmsmq.amqsample.component.DlqPublisher;
import com.jmsmq.amqsample.model.CustomerFinancialSnapshot;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract base class for financial data processors that implements common functionality
 * with generic transformation support
 * 
 * @param <M> The model type from CustomerFinancialSnapshot
 * @param <E> The entity type to transform to
 */
public abstract class AbstractGenericFinancialDataProcessor<M, E> implements FinancialDataProcessor {

    private final String dataType;
    private final DlqPublisher dlqPublisher;
    private final MeterRegistry meterRegistry;
    private final EntityTransformer<M, E> transformer;

    public AbstractGenericFinancialDataProcessor(
            String dataType, 
            DlqPublisher dlqPublisher, 
            MeterRegistry meterRegistry,
            EntityTransformer<M, E> transformer) {
        this.dataType = dataType;
        this.dlqPublisher = dlqPublisher;
        this.meterRegistry = meterRegistry;
        this.transformer = transformer;
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

    /**
     * Subclasses must implement this method to extract the list of model objects from the snapshot
     */
    protected abstract List<M> extractModelList(CustomerFinancialSnapshot snapshot);


    /**
     * Get the transformer
     */
    protected EntityTransformer<M, E> getTransformer() {
        return transformer;
    }

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