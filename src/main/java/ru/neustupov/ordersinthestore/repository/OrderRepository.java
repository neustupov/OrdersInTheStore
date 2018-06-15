package ru.neustupov.ordersinthestore.repository;

import ru.neustupov.ordersinthestore.model.Order;

import java.util.List;

public interface OrderRepository {

    Order save(Order order);

    // null if not found
    Order get(int id);

    List<Order> getAll();

    // false if not found
    boolean delete(int id);
}
