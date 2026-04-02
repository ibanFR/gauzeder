package com.ibanfr.gauzeder.infrastructure.persistence;

import com.ibanfr.gauzeder.domain.model.HelloMessage;
import com.ibanfr.gauzeder.domain.repository.HelloRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

/**
 * JPA-backed implementation of the {@link HelloRepository} port.
 *
 * <p>This class lives in the infrastructure layer and is the only place
 * where JPA / persistence concerns are allowed. It maps between
 * {@link HelloJpaEntity} (infrastructure) and {@link HelloMessage} (domain).
 */
@ApplicationScoped
public class JpaHelloRepository implements HelloRepository {

    @PersistenceContext(unitName = "gauzederPU")
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     *
     * <p>Queries the {@code hello_message} table for the first available row
     * and maps it to a {@link HelloMessage} domain value object.
     *
     * @return an {@link Optional} containing the first greeting, or empty if none exists
     */
    @Override
    public Optional<HelloMessage> findGreeting() {
        TypedQuery<HelloJpaEntity> query = entityManager.createQuery(
                "SELECT h FROM HelloJpaEntity h ORDER BY h.id ASC",
                HelloJpaEntity.class);
        query.setMaxResults(1);

        return query.getResultStream()
                .findFirst()
                .map(entity -> new HelloMessage(entity.getText()));
    }
}
