package com.example.coffee_shop.entity;

import java.util.stream.Stream;

public enum OrderStatus {

    PREPARING,
    FINISHED,
    COLLECTED,
    UNKNOWN;

    public static OrderStatus fromString(String string) {
        return Stream.of(OrderStatus.values())
                .filter(t -> t.name().equalsIgnoreCase(string))
                .findAny().orElse(null);
    }

}
