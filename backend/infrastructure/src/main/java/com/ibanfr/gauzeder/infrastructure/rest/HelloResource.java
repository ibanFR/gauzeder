package com.ibanfr.gauzeder.infrastructure.rest;

import com.ibanfr.gauzeder.application.GetHelloUseCase;
import com.ibanfr.gauzeder.domain.model.HelloMessage;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * JAX-RS resource exposing the Hello World endpoint.
 *
 * <p>No business logic lives here — all logic is delegated to
 * {@link GetHelloUseCase}. This resource only handles HTTP concerns:
 * routing, serialisation, and HTTP status codes.
 */
@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {

    private final GetHelloUseCase getHelloUseCase;

    /**
     * CDI constructor injection.
     *
     * @param getHelloUseCase the use case that retrieves the greeting
     */
    @Inject
    public HelloResource(GetHelloUseCase getHelloUseCase) {
        this.getHelloUseCase = getHelloUseCase;
    }

    /**
     * Returns the Hello World greeting message as a JSON response.
     *
     * @return HTTP 200 with {@link HelloResponse} body, or HTTP 500 if no greeting is configured
     */
    @GET
    public Response getHello() {
        HelloMessage message = getHelloUseCase.execute();
        return Response.ok(new HelloResponse(message.text())).build();
    }
}
