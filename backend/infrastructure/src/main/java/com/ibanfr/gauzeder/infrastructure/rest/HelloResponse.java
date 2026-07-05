package com.ibanfr.gauzeder.infrastructure.rest;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) for the Hello World REST response.
 *
 * <p>This record carries the greeting message from the JAX-RS layer to the client.
 * It is intentionally separate from the domain value object {@code HelloMessage}.
 *
 * @param message the greeting text to return to the client
 */
public record HelloResponse(String message) {

    /**
     * Constructs a {@code HelloResponse} with compact validation.
     *
     * @param message the greeting text, must not be null
     */
    public HelloResponse {
        Objects.requireNonNull(message, "Response message must not be null");
    }
}
