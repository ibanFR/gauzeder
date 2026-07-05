package com.ibanfr.gauzeder.infrastructure.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * JAX-RS application bootstrap.
 *
 * <p>All JAX-RS resources are scanned automatically under the base path {@code /api}.
 * The full URL for the Hello endpoint becomes:
 * {@code http://localhost:8080/gauzeder/api/hello}
 */
@ApplicationPath("/api")
public class JaxRsApplication extends Application {
}
