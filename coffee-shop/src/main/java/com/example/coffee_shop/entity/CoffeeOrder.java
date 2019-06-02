package com.example.coffee_shop.entity;

import com.example.coffee_shop.CoffeeTypeDeserializer;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CoffeeOrder {

    @JsonbTransient
    private final UUID id = UUID.randomUUID();

    @JsonbTypeAdapter(CoffeeTypeDeserializer.class)
    private CoffeeType type;

    @JsonbProperty("status")
    private OrderStatus orderStatus;

    public UUID getId() {
        return id;
    }

    public CoffeeType getType() {
        return type;
    }

    public void setType(CoffeeType type) {
        this.type = type;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "CoffeeOrder{" +
                "id=" + id +
                ", type=" + type +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
