package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.ordersinthestore.model.Model;

@Transactional(readOnly = true)
public interface CrudModelRepository extends JpaRepository<Model, Integer> {

    @Override
    @Transactional
    Model save(Model model);

    @Transactional
    @Query("DELETE FROM Model m WHERE m.id=:id")
    @Modifying
    int delete(@Param("id") int id);
}
