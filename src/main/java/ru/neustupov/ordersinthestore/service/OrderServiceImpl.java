package ru.neustupov.ordersinthestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.neustupov.ordersinthestore.model.Order;
import ru.neustupov.ordersinthestore.repository.OrderRepository;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.util.List;

import static ru.neustupov.ordersinthestore.util.ValidationUtil.checkNotFoundWithId;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Autowired
    public OrderServiceImpl(OrderRepository repository){
        this.repository = repository;
    }

    @Override
    public Order get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("orders")
    @Override
    public List<Order> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "orders", allEntries = true)
    @Override
    public Order create(Order order) {
        Assert.notNull(order, "order must not be null");
        return repository.save(order);
    }

    @CacheEvict(value = "orders", allEntries = true)
    @Override
    public void update(Order order) {
        Assert.notNull(order, "order must not be null");
        checkNotFoundWithId(repository.save(order), order.getId());
    }

    @CacheEvict(value = "orders", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }
}
