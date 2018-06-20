package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.ordersinthestore.model.Brand;

@Transactional(readOnly = true)
public interface CrudBrandRepository extends JpaRepository<Brand, Integer> {

    @Override
    @Transactional
    Brand save(Brand brand);

    @Transactional
    @Query("DELETE FROM Brand b WHERE b.id=:id")
    @Modifying
    int delete(@Param("id") int id);
}
