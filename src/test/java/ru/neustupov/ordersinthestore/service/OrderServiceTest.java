package ru.neustupov.ordersinthestore.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.neustupov.ordersinthestore.model.Order;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

import static ru.neustupov.ordersinthestore.ClientTestData.ANDREY_IVANOV;
import static ru.neustupov.ordersinthestore.OrderTestData.*;

public class OrderServiceTest extends AbstractServiceTest{

    @Autowired
    private OrderService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("orders").clear();
    }

    @Test
    public void create() throws Exception {
        Order newOrder = new Order(null, ANDREY_IVANOV,
                LocalDateTime.of(2018, 2, 10, 10, 0, 0 ), 1000,
                10000, false, null);
        Order created = service.create(newOrder);
        newOrder.setId(created.getId());
        assertMatch(service.getAll(), ORDER_ONE, ORDER_TWO, ORDER_THREE, ORDER_FOUR, ORDER_FIVE,
                ORDER_SIX, newOrder);
    }

    @Test(expected = DataAccessException.class)
    public void duplicatePhoneNumberCreate() throws Exception {
        service.create(new Order(null, ANDREY_IVANOV,
                LocalDateTime.of(2018, 1, 10, 10, 0, 0 ), 1000,
                10000, false, null));
    }

    @Test
    public void delete() throws Exception {
        service.delete(ORDER_ONE_ID);
        assertMatch(service.getAll(),ORDER_TWO, ORDER_THREE, ORDER_FOUR, ORDER_FIVE, ORDER_SIX);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        Order order = service.get(ORDER_ONE_ID);
        assertMatch(order, ORDER_ONE);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void update() throws Exception {
        Order updated = new Order(ORDER_ONE);
        updated.setReady(false);
        service.update(updated);
        assertMatch(service.get(ORDER_ONE_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Order> all = service.getAll();
        assertMatch(all, ORDER_ONE, ORDER_TWO, ORDER_THREE, ORDER_FOUR, ORDER_FIVE, ORDER_SIX);
    }
}
