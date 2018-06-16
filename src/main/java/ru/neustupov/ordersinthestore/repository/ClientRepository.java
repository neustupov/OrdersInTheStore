package ru.neustupov.ordersinthestore.repository;

import ru.neustupov.ordersinthestore.model.Client;

import java.util.List;

public interface ClientRepository {

    Client save(Client client);

    // null if not found
    Client get(int id);

    List<Client> getAll();

    // false if not found
    boolean delete(int id);
}
