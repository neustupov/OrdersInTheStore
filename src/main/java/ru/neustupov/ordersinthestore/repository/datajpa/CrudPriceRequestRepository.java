package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.ordersinthestore.model.PriceRequest;

@Transactional(readOnly = true)
public interface CrudPriceRequestRepository extends JpaRepository<PriceRequest, Integer> {

    @Override
    @Transactional
    PriceRequest save(PriceRequest priceRequest);

    @Transactional
    @Query("DELETE FROM PriceRequest p WHERE p.id=:id")
    @Modifying
    int delete(@Param("id") int id);

}
