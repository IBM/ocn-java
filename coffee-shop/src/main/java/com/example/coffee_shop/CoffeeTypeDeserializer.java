package com.example.coffee_shop;

import com.example.coffee_shop.entity.CoffeeType;

import javax.json.bind.adapter.JsonbAdapter;

public class CoffeeTypeDeserializer implements JsonbAdapter<CoffeeType, String> {

    @Override
    public String adaptToJson(CoffeeType type) {
        return type.name();
    }

    @Override
    public CoffeeType adaptFromJson(String type) {
        return CoffeeType.fromString(type);
    }
}
