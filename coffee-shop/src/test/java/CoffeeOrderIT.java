import com.example.coffee_shop.entity.CoffeeOrder;
import com.example.coffee_shop.entity.CoffeeType;
import com.example.coffee_shop.entity.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.UUID;

class CoffeeOrderIT {

    private EntityManager entityManager;
    private EntityTransaction transaction;

    @BeforeEach
    void setUp() {
        entityManager = Persistence.createEntityManagerFactory("it").createEntityManager();
        transaction = entityManager.getTransaction();
    }

    @Test
    void test() {
        transaction.begin();

        CoffeeOrder order = new CoffeeOrder();
        order.setId(UUID.randomUUID().toString());
        order.setType(CoffeeType.ESPRESSO);
        order.setOrderStatus(OrderStatus.PREPARING);

        entityManager.merge(order);

        transaction.commit();

        List<CoffeeOrder> orders = entityManager.createNamedQuery(CoffeeOrder.FIND_ALL, CoffeeOrder.class)
                .getResultList();

        System.out.println(orders);
    }

}