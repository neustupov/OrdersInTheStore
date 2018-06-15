package ru.neustupov.ordersinthestore.repository;

import ru.neustupov.ordersinthestore.model.PriceRequest;

import java.util.List;

public interface PriceRequestRepository {

    PriceRequest save(PriceRequest priceRequest);

    // null if not found
    PriceRequest get(int id);

    List<PriceRequest> getAll();

    // false if not found
    boolean delete(int id);
}
