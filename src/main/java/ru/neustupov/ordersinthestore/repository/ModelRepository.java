package ru.neustupov.ordersinthestore.repository;

import ru.neustupov.ordersinthestore.model.Model;

import java.util.List;

public interface ModelRepository {
    Model save(Model model);

    // null if not found
    Model get(int id);

    List<Model> getAll();

    // false if not found
    boolean delete(int id);
}
