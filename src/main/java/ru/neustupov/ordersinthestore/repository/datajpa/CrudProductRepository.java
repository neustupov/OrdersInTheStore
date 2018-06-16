package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.ordersinthestore.model.Product;

public interface CrudProductRepository extends JpaRepository<Product, Integer> {
    @Override
    @Transactional
    Product save(Product product);

    @Transactional
    @Query("DELETE FROM Product p WHERE p.id=:id")
    @Modifying
    int delete(@Param("id") int id);
}
