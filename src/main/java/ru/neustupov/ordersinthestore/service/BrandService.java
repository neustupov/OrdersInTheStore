package ru.neustupov.ordersinthestore.service;

import ru.neustupov.ordersinthestore.model.Brand;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.util.List;

public interface BrandService {

    Brand get(int id) throws NotFoundException;

    List<Brand> getAll();

    Brand create(Brand brand);

    void update(Brand brand);

    void delete(int id) throws NotFoundException;
}
