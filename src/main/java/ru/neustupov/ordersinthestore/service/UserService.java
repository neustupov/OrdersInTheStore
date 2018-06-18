package ru.neustupov.ordersinthestore.service;

import ru.neustupov.ordersinthestore.model.User;
import ru.neustupov.ordersinthestore.to.UserTo;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    void update(User user);

    void update(UserTo user);

    List<User> getAll();

    void enable(int id, boolean enable);

    User getByEmail(String email) throws NotFoundException;
}
