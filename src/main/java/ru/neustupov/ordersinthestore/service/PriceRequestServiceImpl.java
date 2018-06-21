package ru.neustupov.ordersinthestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.neustupov.ordersinthestore.model.PriceRequest;
import ru.neustupov.ordersinthestore.repository.PriceRequestRepository;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.util.List;

import static ru.neustupov.ordersinthestore.util.ValidationUtil.checkNotFoundWithId;

@Service
public class PriceRequestServiceImpl implements PriceRequestService{

    private final PriceRequestRepository repository;

    @Autowired
    public PriceRequestServiceImpl(PriceRequestRepository repository){
        this.repository = repository;
    }

    @Override
    public PriceRequest get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("priceRequests")
    @Override
    public List<PriceRequest> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "priceRequests", allEntries = true)
    @Override
    public PriceRequest create(PriceRequest priceRequest) {
        Assert.notNull(priceRequest, "priceRequest must not be null");
        return repository.save(priceRequest);
    }

    @CacheEvict(value = "priceRequests", allEntries = true)
    @Override
    public void update(PriceRequest priceRequest) {
        Assert.notNull(priceRequest, "priceRequest must not be null");
        checkNotFoundWithId(repository.save(priceRequest), priceRequest.getId());
    }

    @CacheEvict(value = "priceRequests", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }
}
