package ru.neustupov.ordersinthestore.web.controller.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.ordersinthestore.model.Order;
import ru.neustupov.ordersinthestore.service.OrderService;

import java.util.List;

import static ru.neustupov.ordersinthestore.util.ValidationUtil.assureIdConsistent;
import static ru.neustupov.ordersinthestore.util.ValidationUtil.checkNew;

public class AbstractOrderController  {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderService service;

    public List<Order> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Order get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Order create(Order order) {
        log.info("create {}", order);
        checkNew(order);
        return service.create(order);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Order order, int id) {
        log.info("update {} with id={}", order, id);
        assureIdConsistent(order, id);
        service.update(order);
    }
}
