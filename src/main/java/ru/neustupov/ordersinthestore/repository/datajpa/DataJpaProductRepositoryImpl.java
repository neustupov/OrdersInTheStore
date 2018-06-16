package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.ordersinthestore.model.Product;
import ru.neustupov.ordersinthestore.repository.ProductRepository;

import java.util.List;

public class DataJpaProductRepositoryImpl implements ProductRepository {
    @Autowired
    private CrudProductRepository crudProductRepository;

    @Override
    public Product save(Product product) {
        return crudProductRepository.save(product);
    }

    @Override
    public Product get(int id) {
        return crudProductRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAll() {
        return crudProductRepository.findAll();
    }

    @Override
    public boolean delete(int id) {
        return crudProductRepository.delete(id) != 0;
    }
}
