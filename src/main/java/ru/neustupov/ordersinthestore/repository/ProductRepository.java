package ru.neustupov.ordersinthestore.repository;

import ru.neustupov.ordersinthestore.model.Product;

import java.util.List;

public interface ProductRepository {

    Product save(Product product);

    // null if not found
    Product get(int id);

    List<Product> getAll();

    // false if not found
    boolean delete(int id);
}
