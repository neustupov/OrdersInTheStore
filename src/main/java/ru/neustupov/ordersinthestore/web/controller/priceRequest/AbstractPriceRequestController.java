package ru.neustupov.ordersinthestore.web.controller.priceRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.ordersinthestore.model.PriceRequest;
import ru.neustupov.ordersinthestore.service.PriceRequestService;

import java.util.List;

import static ru.neustupov.ordersinthestore.util.ValidationUtil.assureIdConsistent;
import static ru.neustupov.ordersinthestore.util.ValidationUtil.checkNew;

public class AbstractPriceRequestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PriceRequestService service;

    public List<PriceRequest> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public PriceRequest get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public List<PriceRequest> getAllWithAllInformation() {
        log.info("getAllWithAllInformation");
        return service.getAllWithUserAndClientAndProducts();
    }

    public PriceRequest create(PriceRequest priceRequest) {
        log.info("create {}", priceRequest);
        checkNew(priceRequest);
        return service.create(priceRequest);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(PriceRequest priceRequest, int id) {
        log.info("update {} with id={}", priceRequest, id);
        assureIdConsistent(priceRequest, id);
        service.update(priceRequest);
    }

    public void ready(int id, boolean ready) {
        log.info((ready ? "ready " : "not ready ") + id);
        service.ready(id, ready);
    }
}
