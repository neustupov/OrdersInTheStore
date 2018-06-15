package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.ordersinthestore.model.Order;
import ru.neustupov.ordersinthestore.repository.OrderRepository;

import java.util.List;

public class DataJpaOrderRepositoryImpl implements OrderRepository {

    @Autowired
    private CrudOrderRepository crudOrderRepository;

    @Override
    public Order save(Order order) {
        return crudOrderRepository.save(order);
    }

    @Override
    public Order get(int id) {
        return crudOrderRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> getAll() {
        return crudOrderRepository.findAll();
    }

    @Override
    public boolean delete(int id) {
        return crudOrderRepository.delete(id) != 0;
    }
}
