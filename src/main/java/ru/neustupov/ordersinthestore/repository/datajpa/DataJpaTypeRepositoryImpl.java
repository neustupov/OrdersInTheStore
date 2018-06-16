package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.neustupov.ordersinthestore.model.Type;
import ru.neustupov.ordersinthestore.repository.TypeRepository;

import java.util.List;

@Repository
public class DataJpaTypeRepositoryImpl implements TypeRepository{

    @Autowired
    private CrudTypeRepository crudTypeRepository;

    @Override
    public Type save(Type type) {
        return crudTypeRepository.save(type);
    }

    @Override
    public Type get(int id) {
        return crudTypeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Type> getAll() {
        return crudTypeRepository.findAll();
    }

    @Override
    public boolean delete(int id) {
        return crudTypeRepository.delete(id) != 0;
    }
}
