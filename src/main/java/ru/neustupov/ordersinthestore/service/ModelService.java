package ru.neustupov.ordersinthestore.service;

import ru.neustupov.ordersinthestore.model.Model;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.util.List;

public interface ModelService {

    Model get(int id) throws NotFoundException;

    List<Model> getAll();

    Model create(Model model);

    void update(Model model);

    void delete(int id) throws NotFoundException;
}
