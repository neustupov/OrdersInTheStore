package ru.neustupov.ordersinthestore.web.controller.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.ordersinthestore.model.Product;
import ru.neustupov.ordersinthestore.service.ProductService;

import java.util.List;

import static ru.neustupov.ordersinthestore.util.ValidationUtil.assureIdConsistent;
import static ru.neustupov.ordersinthestore.util.ValidationUtil.checkNew;

public class AbstractProductController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductService service;

    public List<Product> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Product get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Product create(Product product) {
        log.info("create {}", product);
        checkNew(product);
        return service.create(product);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Product product, int id) {
        log.info("update {} with id={}", product, id);
        assureIdConsistent(product, id);
        service.update(product);
    }
}
