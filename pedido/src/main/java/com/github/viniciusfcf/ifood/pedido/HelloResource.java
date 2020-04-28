package com.github.viniciusfcf.ifood.pedido;

import java.time.LocalDateTime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

@Path("/hello")
public class HelloResource {

    private static final Logger LOG = Logger.getLogger(HelloResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        LOG.info("Olá Quarkus");
        LOG.infov("Olá Quarkus {0} ", LocalDateTime.now());
        return "hello";
    }
}