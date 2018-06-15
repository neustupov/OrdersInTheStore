package ru.neustupov.ordersinthestore.repository;

import ru.neustupov.ordersinthestore.model.User;

import java.util.List;

public interface UserRepository {

    User save(User user);

    // null if not found
    User get(int id);

    List<User> getAll();

    // null if not found
    User getByEmail(String email);

    // false if not found
    boolean delete(int id);
}
