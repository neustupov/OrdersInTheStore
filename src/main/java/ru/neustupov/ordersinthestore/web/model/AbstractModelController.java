package ru.neustupov.ordersinthestore.web.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.ordersinthestore.model.Model;
import ru.neustupov.ordersinthestore.service.ModelService;

import java.util.List;

import static ru.neustupov.ordersinthestore.util.ValidationUtil.assureIdConsistent;
import static ru.neustupov.ordersinthestore.util.ValidationUtil.checkNew;

public class AbstractModelController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ModelService service;

    public List<Model> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Model get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Model create(Model model) {
        log.info("create {}", model);
        checkNew(model);
        return service.create(model);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Model model, int id) {
        log.info("update {} with id={}", model, id);
        assureIdConsistent(model, id);
        service.update(model);
    }
}
