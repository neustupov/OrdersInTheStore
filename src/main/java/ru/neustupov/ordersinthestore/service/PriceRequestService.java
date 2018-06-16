package ru.neustupov.ordersinthestore.service;

import ru.neustupov.ordersinthestore.model.PriceRequest;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.util.List;

public interface PriceRequestService {

    PriceRequest get(int id) throws NotFoundException;

    List<PriceRequest> getAll();

    PriceRequest create(PriceRequest priceRequest);

    void update(PriceRequest priceRequest);

    void delete(int id) throws NotFoundException;
}
