package com.example.barista.boundary;

import com.example.barista.entity.CoffeeBrew;

import java.util.Map;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/brews")
public class BrewsResource {

    @Inject
    CoffeeBrews coffeeBrews;

    @GET
    @Path("/{id}")
    public Response retrieveCoffeeBrew(@PathParam("id") String id) {
        CoffeeBrew brew = coffeeBrews.retrieveBrew(id);

        if (brew == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(buildResponse(brew)).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCoffeeBrews() {
        Map<String, CoffeeBrew> brews = coffeeBrews.retrieveBrews();

        return Response.ok(brews).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCoffeeBrew(@PathParam("id") String id, JsonObject jsonObject) {
        String coffeeType = jsonObject.getString("type", null);

        if (coffeeType == null)
            throw new BadRequestException();

        CoffeeBrew brew = coffeeBrews.startBrew(id, coffeeType);

        return Response.status(Response.Status.CREATED)
                .entity(buildResponse(brew))
                .build();
    }

    private JsonObject buildResponse(CoffeeBrew brew) {
        return Json.createObjectBuilder()
                .add("status", brew.getStatus())
                .add("type", brew.getType())
                .build();
    }

}
