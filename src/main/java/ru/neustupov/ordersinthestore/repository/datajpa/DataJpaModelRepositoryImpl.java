package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.ordersinthestore.model.Model;
import ru.neustupov.ordersinthestore.repository.ModelRepository;

import java.util.List;

public class DataJpaModelRepositoryImpl implements ModelRepository {

    @Autowired
    private CrudModelRepository crudModelRepository;

    @Override
    public Model save(Model model) {
        return crudModelRepository.save(model);
    }

    @Override
    public Model get(int id) {
        return crudModelRepository.findById(id).orElse(null);
    }

    @Override
    public List<Model> getAll() {
        return crudModelRepository.findAll();
    }

    @Override
    public boolean delete(int id) {
        return crudModelRepository.delete(id) != 0;
    }
}
