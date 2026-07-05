package com.ibanfr.gauzeder.application;

import com.ibanfr.gauzeder.domain.event.HelloRequestedEvent;
import com.ibanfr.gauzeder.domain.model.HelloMessage;
import com.ibanfr.gauzeder.domain.repository.HelloRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * Application-layer use case for retrieving the Hello World greeting.
 *
 * <p>This bean orchestrates the domain logic: it calls the repository port,
 * raises a {@link HelloRequestedEvent} via CDI after the message is found,
 * and returns the {@link HelloMessage} value object to the caller.
 *
 * <p>No business logic lives here — orchestration only.
 */
@ApplicationScoped
public class GetHelloUseCase {

    private final HelloRepository helloRepository;
    private final Event<HelloRequestedEvent> helloRequestedEvents;

    /**
     * CDI constructor injection.
     *
     * @param helloRepository     the repository port for greeting messages
     * @param helloRequestedEvents the CDI event channel for {@link HelloRequestedEvent}
     */
    @Inject
    public GetHelloUseCase(HelloRepository helloRepository,
                           Event<HelloRequestedEvent> helloRequestedEvents) {
        this.helloRepository = helloRepository;
        this.helloRequestedEvents = helloRequestedEvents;
    }

    /**
     * Executes the use case: retrieves the greeting and fires a domain event.
     *
     * @return the {@link HelloMessage} found in the repository
     * @throws IllegalStateException if no greeting has been configured
     */
    @Transactional
    public HelloMessage execute() {
        HelloMessage message = helloRepository.findGreeting()
                .orElseThrow(() -> new IllegalStateException(
                        "No greeting configured. Ensure the database is seeded with import.sql."));

        helloRequestedEvents.fire(HelloRequestedEvent.now());

        return message;
    }
}
