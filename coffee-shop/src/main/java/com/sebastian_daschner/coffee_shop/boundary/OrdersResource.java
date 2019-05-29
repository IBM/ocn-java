package com.sebastian_daschner.coffee_shop.boundary;

import com.sebastian_daschner.coffee_shop.entity.CoffeeOrder;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.opentracing.Traced;


@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @Inject
    CoffeeShop coffeeShop;

    @Context
    UriInfo uriInfo;

    @Context
    HttpServletRequest request;

    @GET
    public List<CoffeeOrder> getOrders() {
        return coffeeShop.getOrders();
    }

    private JsonObject buildOrder(CoffeeOrder order) {
        return Json.createObjectBuilder()
                .add("type", order.getType().name())
                .add("status", order.getOrderStatus().name())
                .add("_self", buildUri(order).toString())
                .build();
    }

    public JsonArray emptyOrders() {
        return Json.createArrayBuilder().build();
    }

    @GET
    @Path("{id}")
    @Operation(summary="Get a coffee order", 
               description="Returns a CoffeeOrder object for the given order id.")
    public CoffeeOrder getOrder(@PathParam("id") UUID id) {
        return coffeeShop.getOrder(id);
    }

    @POST
    @Timed(
        name="orderCoffee.timer",
        displayName="Timings to Coffee Orders",
        description = "Time taken to place a new coffee order.")
    public Response orderCoffee(@Valid @NotNull CoffeeOrder order) {
        final CoffeeOrder storedOrder = coffeeShop.orderCoffee(order);
        return Response.created(buildUri(storedOrder)).build();
    }

    private URI buildUri(CoffeeOrder order) {
        return uriInfo.getBaseUriBuilder()
                .host(request.getServerName())
                .port(request.getServerPort())
                .path(OrdersResource.class)
                .path(OrdersResource.class, "getOrder")
                .build(order.getId());
    }
}
