package com.jmsmq.amqsample.component.processors.financialdata;

/**
 * Generic interface for transforming model objects to entity objects
 * @param <M> The model type
 * @param <E> The entity type
 */
public interface EntityTransformer<M, E> {
    
    /**
     * Transform a model object to an entity object
     * @param model The model object to transform
     * @return The transformed entity object
     */
    E transformToEntity(M model);
}