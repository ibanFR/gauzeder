package com.ibanfr.gauzeder.infrastructure.rest;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

/**
 * JAX-RS response filter that adds CORS headers to every API response.
 *
 * <p>This filter allows the React development server running on
 * {@code http://localhost:5173} to call the backend during local development.
 * In production, configure a reverse proxy instead of relying on this filter.
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    private static final String ALLOWED_ORIGIN = "http://localhost:5173";

    /**
     * Adds CORS headers to the outgoing response.
     *
     * @param requestContext  the inbound request context
     * @param responseContext the outbound response context to which headers are added
     * @throws IOException if an I/O error occurs (not expected in this implementation)
     */
    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
        responseContext.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        responseContext.getHeaders().add("Access-Control-Allow-Headers",
                "Content-Type, Authorization, Accept");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
    }
}
