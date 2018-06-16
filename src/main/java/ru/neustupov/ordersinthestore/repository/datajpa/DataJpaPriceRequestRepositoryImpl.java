package ru.neustupov.ordersinthestore.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.neustupov.ordersinthestore.model.PriceRequest;
import ru.neustupov.ordersinthestore.repository.PriceRequestRepository;

import java.util.List;

@Repository
public class DataJpaPriceRequestRepositoryImpl implements PriceRequestRepository{

    @Autowired
    private CrudPriceRequestRepository crudPriceRequestRepository;

    @Override
    public PriceRequest save(PriceRequest priceRequest) {
        return crudPriceRequestRepository.save(priceRequest);
    }

    @Override
    public PriceRequest get(int id) {
        return crudPriceRequestRepository.findById(id).orElse(null);
    }

    @Override
    public List<PriceRequest> getAll() {
        return crudPriceRequestRepository.findAll();
    }

    @Override
    public boolean delete(int id) {
        return crudPriceRequestRepository.delete(id) != 0;
    }
}
