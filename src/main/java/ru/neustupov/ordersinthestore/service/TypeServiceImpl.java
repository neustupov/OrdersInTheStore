package ru.neustupov.ordersinthestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.neustupov.ordersinthestore.model.Type;
import ru.neustupov.ordersinthestore.repository.TypeRepository;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.util.List;

import static ru.neustupov.ordersinthestore.util.ValidationUtil.checkNotFoundWithId;

@Service
public class TypeServiceImpl implements TypeService{

    private final TypeRepository repository;

    @Autowired
    public TypeServiceImpl(TypeRepository repository){
        this.repository = repository;
    }

    @Override
    public Type get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("type")
    @Override
    public List<Type> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "type", allEntries = true)
    @Override
    public Type create(Type type) {
        Assert.notNull(type, "type must not be null");
        return repository.save(type);
    }

    @CacheEvict(value = "type", allEntries = true)
    @Override
    public void update(Type type) {
        Assert.notNull(type, "type must not be null");
        checkNotFoundWithId(repository.save(type), type.getId());
    }

    @CacheEvict(value = "type", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }
}
