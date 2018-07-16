package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.ordersinthestore.model.PriceRequest;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudPriceRequestRepository extends JpaRepository<PriceRequest, Integer> {

    @Override
    @Transactional
    PriceRequest save(PriceRequest priceRequest);

    @Transactional
    @Query("DELETE FROM PriceRequest p WHERE p.id=:id")
    @Modifying
    int delete(@Param("id") int id);

    @EntityGraph(attributePaths = {"user", "client", "products"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT p FROM PriceRequest p")
    public List<PriceRequest> getAllWithUserAndClientAndProducts();

}
