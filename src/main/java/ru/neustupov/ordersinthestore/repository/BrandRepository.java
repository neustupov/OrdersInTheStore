package ru.neustupov.ordersinthestore.repository;

import ru.neustupov.ordersinthestore.model.Brand;

import java.util.List;

public interface BrandRepository {

    Brand save(Brand brand);

    // null if not found
    Brand get(int id);

    List<Brand> getAll();

    // false if not found
    boolean delete(int id);
}
