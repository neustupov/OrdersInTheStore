package ru.neustupov.ordersinthestore.repository;

import ru.neustupov.ordersinthestore.model.Type;

import java.util.List;

public interface TypeRepository {
    Type save(Type type);

    // null if not found
    Type get(int id);

    List<Type> getAll();

    // false if not found
    boolean delete(int id);
}
