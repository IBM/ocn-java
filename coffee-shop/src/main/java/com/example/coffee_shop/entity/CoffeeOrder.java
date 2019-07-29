package com.example.coffee_shop.entity;

import com.example.coffee_shop.CoffeeTypeAdapter;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;

import static com.example.coffee_shop.entity.CoffeeOrder.FIND_ALL;
import static com.example.coffee_shop.entity.CoffeeOrder.FIND_UNFINISHED;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = FIND_UNFINISHED, query = "select o from CoffeeOrder o where o.orderStatus <> " +
                "com.example.coffee_shop.entity.OrderStatus.COLLECTED"),
        @NamedQuery(name = FIND_ALL, query = "select o from CoffeeOrder o")})
public class CoffeeOrder {

    public static final String FIND_UNFINISHED = "Order.findUnfinished";
    public static final String FIND_ALL = "Order.findAll";

    @Id
    @JsonbTransient
    private String id;

    @JsonbTypeAdapter(CoffeeTypeAdapter.class)
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "coffee_type")
    private CoffeeType type;

    @JsonbProperty("status")
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
