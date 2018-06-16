package ru.neustupov.ordersinthestore.service;

import ru.neustupov.ordersinthestore.model.Order;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.util.List;

public interface OrderService {

    Order get(int id) throws NotFoundException;

    List<Order> getAll();

    Order create(Order order);

    void update(Order order);

    void delete(int id) throws NotFoundException;
}
