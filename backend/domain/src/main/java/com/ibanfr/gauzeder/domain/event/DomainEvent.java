package com.ibanfr.gauzeder.domain.event;

/**
 * Marker interface for all domain events in the Gauzeder bounded context.
 *
 * <p>Domain events represent something meaningful that happened in the domain.
 * Implementations should be immutable value objects (records).
 *
 * <p>Events are raised inside aggregates or use cases and published only
 * after the transaction boundary has been committed.
 */
public interface DomainEvent {
}
