package ru.neustupov.ordersinthestore.web.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.ordersinthestore.model.Client;
import ru.neustupov.ordersinthestore.service.ClientService;

import java.util.List;

import static ru.neustupov.ordersinthestore.util.ValidationUtil.assureIdConsistent;
import static ru.neustupov.ordersinthestore.util.ValidationUtil.checkNew;

public class AbstractClientController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClientService service;

    public List<Client> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Client get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Client create(Client client) {
        log.info("create {}", client);
        checkNew(client);
        return service.create(client);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Client client, int id) {
        log.info("update {} with id={}", client, id);
        assureIdConsistent(client, id);
        service.update(client);
    }
}
