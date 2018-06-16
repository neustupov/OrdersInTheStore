package ru.neustupov.ordersinthestore.service;

import ru.neustupov.ordersinthestore.model.Client;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.util.List;

public interface ClientService {

    Client get(int id) throws NotFoundException;

    List<Client> getAll();

    Client create(Client client);

    void update(Client client);

    void delete(int id) throws NotFoundException;
}
