package com.example.coffee_shop.boundary;

import com.example.coffee_shop.control.Barista;
import com.example.coffee_shop.control.OrderProcessor;
import com.example.coffee_shop.entity.CoffeeOrder;
import com.example.coffee_shop.entity.CoffeeType;
import com.example.coffee_shop.entity.OrderStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Stateless
public class CoffeeShop {

    @Inject
    Barista barista;

    @Inject
    OrderProcessor orderProcessor;

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    @ConfigProperty(name = "coffeeShop.order.defaultCoffeeType", defaultValue = "ESPRESSO")
    private CoffeeType defaultCoffeeType;

    public List<CoffeeOrder> getOrders() {
        return entityManager.createNamedQuery(CoffeeOrder.FIND_ALL, CoffeeOrder.class)
                .getResultList();
    }

    public CoffeeOrder getOrder(UUID id) {
        return entityManager.find(CoffeeOrder.class, id.toString());
    }

    public CoffeeOrder orderCoffee(CoffeeOrder order) {
        order.setId(UUID.randomUUID().toString());
        setDefaultType(order);
        OrderStatus status = barista.brewCoffee(order);
        order.setOrderStatus(status);

        return entityManager.merge(order);
    }

    private void setDefaultType(CoffeeOrder order) {
        if (order.getType() == null)
            order.setType(defaultCoffeeType);
    }

    public void processUnfinishedOrders() {
        entityManager.createNamedQuery(CoffeeOrder.FIND_UNFINISHED, CoffeeOrder.class)
                .getResultList()
                .forEach(orderProcessor::processOrder);
    }

}
