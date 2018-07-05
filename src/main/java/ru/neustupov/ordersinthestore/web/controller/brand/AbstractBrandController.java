package ru.neustupov.ordersinthestore.web.controller.brand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.ordersinthestore.model.Brand;
import ru.neustupov.ordersinthestore.service.BrandService;

import java.util.List;

import static ru.neustupov.ordersinthestore.util.ValidationUtil.assureIdConsistent;
import static ru.neustupov.ordersinthestore.util.ValidationUtil.checkNew;

public class AbstractBrandController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private BrandService service;

    public List<Brand> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Brand get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Brand create(Brand brand) {
        log.info("create {}", brand);
        checkNew(brand);
        return service.create(brand);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Brand brand, int id) {
        log.info("update {} with id={}", brand, id);
        assureIdConsistent(brand, id);
        service.update(brand);
    }
}
