package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.neustupov.ordersinthestore.model.User;
import ru.neustupov.ordersinthestore.repository.UserRepository;

import java.util.List;

@Repository
public class DataJpaUserRepositoryImpl implements UserRepository {

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public User save(User user) {
        return crudUserRepository.save(user);
    }

    @Override
    public User get(int id) {
        return crudUserRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAll() {
        return crudUserRepository.getAll();
    }

    @Override
    public User getByEmail(String email) {
        return crudUserRepository.getByEmail(email);
    }

    @Override
    public boolean delete(int id) {
        return crudUserRepository.delete(id) != 0;
    }
}
