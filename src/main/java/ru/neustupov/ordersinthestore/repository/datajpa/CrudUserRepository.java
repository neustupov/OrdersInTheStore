package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.ordersinthestore.model.User;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer>{

    @Override
    @Transactional
    User save(User user);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name")
    List<User> getAll();

    User getByEmail(String email);

    @Transactional
    @Query("DELETE FROM User u WHERE u.id=:id")
    @Modifying
    int delete(@Param("id") int id);
}
