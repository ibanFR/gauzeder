package com.ibanfr.gauzeder.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA entity for persisting greeting messages.
 *
 * <p>This class is intentionally separate from the domain value object
 * {@code HelloMessage}. Domain classes must never carry JPA annotations —
 * that would couple the domain layer to infrastructure concerns.
 *
 * <p>Mapping between this entity and the domain model is performed in
 * {@link JpaHelloRepository}.
 */
@Entity
@Table(name = "hello_message")
public class HelloJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "text", nullable = false, length = 255)
    private String text;

    /** Required no-arg constructor for JPA. */
    protected HelloJpaEntity() {
    }

    /**
     * Creates a new {@code HelloJpaEntity} with the given identity and text.
     *
     * @param id   the primary key
     * @param text the greeting text
     */
    public HelloJpaEntity(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    /**
     * Returns the primary key.
     *
     * @return the entity id
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the greeting text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }
}
