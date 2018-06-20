package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.neustupov.ordersinthestore.model.Brand;
import ru.neustupov.ordersinthestore.repository.BrandRepository;

import java.util.List;

@Repository
public class DataJpaBrandRepositoryImpl implements BrandRepository{

    @Autowired
    private CrudBrandRepository crudBrandRepository;

    @Override
    public Brand save(Brand brand) {
        return crudBrandRepository.save(brand);
    }

    @Override
    public Brand get(int id) {
        return crudBrandRepository.findById(id).orElse(null);
    }

    @Override
    public List<Brand> getAll() {
        return crudBrandRepository.findAll();
    }

    @Override
    public boolean delete(int id) {
        return crudBrandRepository.delete(id) != 0;
    }
}
