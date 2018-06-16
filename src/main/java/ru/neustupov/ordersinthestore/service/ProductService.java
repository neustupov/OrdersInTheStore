package ru.neustupov.ordersinthestore.service;

import ru.neustupov.ordersinthestore.model.Product;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.util.List;

public interface ProductService {

    Product get(int id) throws NotFoundException;

    List<Product> getAll();

    Product create(Product product);

    void update(Product product);

    void delete(int id) throws NotFoundException;
}
