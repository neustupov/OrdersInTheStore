package ru.neustupov.ordersinthestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.neustupov.ordersinthestore.model.Product;
import ru.neustupov.ordersinthestore.repository.ProductRepository;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.util.List;

import static ru.neustupov.ordersinthestore.util.ValidationUtil.checkNotFoundWithId;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository){
        this.repository = repository;
    }

    @Override
    public Product get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("product")
    @Override
    public List<Product> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "product", allEntries = true)
    @Override
    public Product create(Product product) {
        Assert.notNull(product, "product must not be null");
        return repository.save(product);
    }

    @CacheEvict(value = "product", allEntries = true)
    @Override
    public void update(Product product) {
        Assert.notNull(product, "product must not be null");
        checkNotFoundWithId(repository.save(product), product.getId());
    }

    @CacheEvict(value = "product", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }
}
