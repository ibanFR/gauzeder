package com.ibanfr.gauzeder.domain.model;

import java.util.Objects;

/**
 * Value object representing a greeting message in the Gauzeder domain.
 *
 * <p>This is a pure domain concept with no persistence or framework annotations.
 * Validation is enforced at construction time via the compact constructor.
 *
 * @param text the greeting text, must not be null or blank
 */
public record HelloMessage(String text) {

    /**
     * Compact constructor that validates the greeting text.
     *
     * @param text the greeting text
     * @throws NullPointerException     if {@code text} is null
     * @throws IllegalArgumentException if {@code text} is blank
     */
    public HelloMessage {
        Objects.requireNonNull(text, "Greeting text must not be null");
        if (text.isBlank()) {
            throw new IllegalArgumentException("Greeting text must not be blank");
        }
    }
}
