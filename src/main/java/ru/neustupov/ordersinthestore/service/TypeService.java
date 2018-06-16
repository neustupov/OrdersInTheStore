package ru.neustupov.ordersinthestore.service;

import ru.neustupov.ordersinthestore.model.Type;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.util.List;

public interface TypeService {

    Type get(int id) throws NotFoundException;

    List<Type> getAll();

    Type create(Type type);

    void update(Type type);

    void delete(int id) throws NotFoundException;
}
