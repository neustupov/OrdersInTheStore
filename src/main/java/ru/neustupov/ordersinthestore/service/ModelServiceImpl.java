package ru.neustupov.ordersinthestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.neustupov.ordersinthestore.model.Model;
import ru.neustupov.ordersinthestore.repository.ModelRepository;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.util.List;

import static ru.neustupov.ordersinthestore.util.ValidationUtil.checkNotFoundWithId;

@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository repository;

    @Autowired
    public ModelServiceImpl(ModelRepository repository){
        this.repository = repository;
    }

    @Override
    public Model get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("models")
    @Override
    public List<Model> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "models", allEntries = true)
    @Override
    public Model create(Model model) {
        Assert.notNull(model, "model must not be null");
        return repository.save(model);
    }

    @CacheEvict(value = "models", allEntries = true)
    @Override
    public void update(Model model) {
        Assert.notNull(model, "model must not be null");
        checkNotFoundWithId(repository.save(model), model.getId());
    }

    @CacheEvict(value = "models", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }
}
