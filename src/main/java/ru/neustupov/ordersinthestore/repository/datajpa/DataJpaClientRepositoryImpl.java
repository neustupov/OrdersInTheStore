package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.ordersinthestore.model.Client;
import ru.neustupov.ordersinthestore.repository.ClientRepository;

import java.util.List;

public class DataJpaClientRepositoryImpl implements ClientRepository{

    @Autowired
    private CrudClientRepository crudClientRepository;

    @Override
    public Client save(Client client) {
        return crudClientRepository.save(client);
    }

    @Override
    public Client get(int id) {
        return crudClientRepository.findById(id).orElse(null);
    }

    @Override
    public List<Client> getAll() {
        return crudClientRepository.findAll();
    }

    @Override
    public boolean delete(int id) {
        return crudClientRepository.delete(id) != 0;
    }
}
