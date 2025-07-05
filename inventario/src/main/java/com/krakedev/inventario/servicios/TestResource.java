package com.krakedev.inventario.servicios;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class TestResource {

    @GET
    @Path("/{param}")
    @Produces(MediaType.TEXT_PLAIN)
    public String test(@PathParam("param") String param) {
        System.out.println("Param recibido: [" + param + "]");
        return "Param recibido: " + param;
    }
}
