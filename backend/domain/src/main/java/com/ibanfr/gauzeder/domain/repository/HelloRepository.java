package com.ibanfr.gauzeder.domain.repository;

import com.ibanfr.gauzeder.domain.model.HelloMessage;

import java.util.Optional;

/**
 * Repository port (interface) for accessing greeting messages.
 *
 * <p>This interface is defined in the domain layer; its implementation lives
 * in the infrastructure layer (dependency inversion principle).
 * The domain layer has no knowledge of JPA, databases, or any other persistence mechanism.
 */
public interface HelloRepository {

    /**
     * Finds the configured greeting message.
     *
     * @return an {@link Optional} containing the {@link HelloMessage} if one exists,
     *         or an empty {@link Optional} if no greeting has been configured
     */
    Optional<HelloMessage> findGreeting();
}
