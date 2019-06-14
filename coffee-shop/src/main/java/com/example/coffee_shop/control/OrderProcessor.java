package com.example.coffee_shop.control;


import com.example.coffee_shop.entity.CoffeeOrder;
import com.example.coffee_shop.entity.OrderStatus;

import org.eclipse.microprofile.opentracing.Traced;

import javax.inject.Inject;

public class OrderProcessor {

    @Inject
    Barista barista;

    @Traced
    public void processOrder(CoffeeOrder order) {
        OrderStatus status = barista.retrieveBrewStatus(order);
        order.setOrderStatus(status);
    }

}
