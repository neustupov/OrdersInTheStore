package ru.neustupov.ordersinthestore.web.controller.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.ordersinthestore.model.Type;
import ru.neustupov.ordersinthestore.service.TypeService;

import java.util.List;

import static ru.neustupov.ordersinthestore.util.ValidationUtil.assureIdConsistent;
import static ru.neustupov.ordersinthestore.util.ValidationUtil.checkNew;

public abstract class AbstractTypeController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TypeService service;

    public List<Type> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Type get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Type create(Type type) {
        log.info("create {}", type);
        checkNew(type);
        return service.create(type);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Type type, int id) {
        log.info("update {} with id={}", type, id);
        assureIdConsistent(type, id);
        service.update(type);
    }
}
