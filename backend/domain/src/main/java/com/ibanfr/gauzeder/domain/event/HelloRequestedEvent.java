package com.ibanfr.gauzeder.domain.event;

import java.time.Instant;
import java.util.Objects;

/**
 * Domain event raised when a Hello greeting is requested by a client.
 *
 * <p>This event is emitted by {@code GetHelloUseCase} after the greeting
 * has been successfully retrieved from the repository.
 *
 * @param requestedAt the instant at which the greeting was requested
 */
public record HelloRequestedEvent(Instant requestedAt) implements DomainEvent {

    /**
     * Constructs a {@code HelloRequestedEvent} with compact validation.
     *
     * @param requestedAt the instant at which the greeting was requested, must not be null
     */
    public HelloRequestedEvent {
        Objects.requireNonNull(requestedAt, "requestedAt must not be null");
    }

    /**
     * Factory method that creates a {@code HelloRequestedEvent} with the current instant.
     *
     * @return a new event timestamped to now
     */
    public static HelloRequestedEvent now() {
        return new HelloRequestedEvent(Instant.now());
    }
}
