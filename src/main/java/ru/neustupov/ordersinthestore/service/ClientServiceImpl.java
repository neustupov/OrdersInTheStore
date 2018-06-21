package ru.neustupov.ordersinthestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.neustupov.ordersinthestore.model.Client;
import ru.neustupov.ordersinthestore.repository.ClientRepository;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.util.List;

import static ru.neustupov.ordersinthestore.util.ValidationUtil.checkNotFoundWithId;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    @Autowired
    public ClientServiceImpl(ClientRepository repository){
        this.repository = repository;
    }

    @Override
    public Client get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("clients")
    @Override
    public List<Client> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "clients", allEntries = true)
    @Override
    public Client create(Client client) {
        Assert.notNull(client, "client must not be null");
        return repository.save(client);
    }

    @CacheEvict(value = "clients", allEntries = true)
    @Override
    public void update(Client client) {
        Assert.notNull(client, "client must not be null");
        checkNotFoundWithId(repository.save(client), client.getId());
    }

    @CacheEvict(value = "clients", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }
}
