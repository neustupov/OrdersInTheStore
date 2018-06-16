package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.ordersinthestore.model.Type;

public interface CrudTypeRepository extends JpaRepository<Type, Integer> {
    @Override
    @Transactional
    Type save(Type type);

    @Transactional
    @Query("DELETE FROM Type t WHERE t.id=:id")
    @Modifying
    int delete(@Param("id") int id);
}
