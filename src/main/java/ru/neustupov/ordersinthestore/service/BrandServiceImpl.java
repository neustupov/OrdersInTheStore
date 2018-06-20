package ru.neustupov.ordersinthestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.neustupov.ordersinthestore.model.Brand;
import ru.neustupov.ordersinthestore.repository.BrandRepository;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.util.List;

import static ru.neustupov.ordersinthestore.util.ValidationUtil.checkNotFoundWithId;

@Service
public class BrandServiceImpl implements BrandService{

    private final BrandRepository repository;

    @Autowired
    public BrandServiceImpl(BrandRepository repository){
        this.repository = repository;
    }

    @Override
    public Brand get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("brands")
    @Override
    public List<Brand> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "brands", allEntries = true)
    @Override
    public Brand create(Brand brand) {
        Assert.notNull(brand, "brand must not be null");
        return repository.save(brand);
    }

    @CacheEvict(value = "brands", allEntries = true)
    @Override
    public void update(Brand brand) {
        Assert.notNull(brand, "brand must not be null");
        checkNotFoundWithId(repository.save(brand), brand.getId());
    }

    @CacheEvict(value = "brands", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }
}
