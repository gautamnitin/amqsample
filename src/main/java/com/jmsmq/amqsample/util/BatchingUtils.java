package com.jmsmq.amqsample.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Generic batching utilities to process a single stream and consume items in batches.
 * This class is framework-agnostic and can be reused across projects.
 */
public final class BatchingUtils {

    private BatchingUtils() {}

    /**
     * Processes a single stream by mapping each input element to an output element and passing
     * them to the provided batch consumer in fixed-size batches. Uses a single pass over the stream.
     *
     * - The mapper is applied as items flow through the stream (no pre-collection).
     * - Batches are delivered to the consumer as soon as they reach the requested size.
     * - The final partial batch (if any) is also delivered.
     *
     * @param source       The source stream of input items
     * @param mapper       Function to map input items to output items
     * @param batchSize    Desired batch size (> 0)
     * @param batchConsumer Consumer that will receive each batch
     * @param <TIn>        Input item type
     * @param <TOut>       Output item type
     */
    public static <TIn, TOut> void mapAndBatch(
            Stream<TIn> source,
            Function<? super TIn, ? extends TOut> mapper,
            int batchSize,
            Consumer<List<TOut>> batchConsumer
    ) {
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(mapper, "mapper");
        Objects.requireNonNull(batchConsumer, "batchConsumer");
        if (batchSize <= 0) {
            throw new IllegalArgumentException("batchSize must be > 0");
        }

        List<TOut> buffer = new ArrayList<>(batchSize);
        source.map(mapper).forEach(item -> {
            buffer.add(item);
            if (buffer.size() >= batchSize) {
                // hand over a defensive copy to allow downstream to keep the list
                batchConsumer.accept(new ArrayList<>(buffer));
                buffer.clear();
            }
        });
        if (!buffer.isEmpty()) {
            batchConsumer.accept(new ArrayList<>(buffer));
            buffer.clear();
        }
    }
}
