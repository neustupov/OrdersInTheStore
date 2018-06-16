package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.ordersinthestore.model.Client;

@Transactional(readOnly = true)
public interface CrudClientRepository extends JpaRepository<Client, Integer> {

    @Override
    @Transactional
    Client save(Client client);

    @Transactional
    @Query("DELETE FROM Client c WHERE c.id=:id")
    @Modifying
    int delete(@Param("id") int id);
}
