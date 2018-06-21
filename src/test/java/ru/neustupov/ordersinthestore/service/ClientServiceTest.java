package ru.neustupov.ordersinthestore.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.neustupov.ordersinthestore.model.Client;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static ru.neustupov.ordersinthestore.ClientTestData.*;

public class ClientServiceTest extends AbstractServiceTest{

    @Autowired
    private ClientService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("clients").clear();
    }

    @Test
    public void create() throws Exception {
        Client newClient = new Client(null, "NewName", "NewLastName",
                1236547891L, null, null);
        Client created = service.create(newClient);
        newClient.setId(created.getId());
        assertMatch(service.getAll(), ANDREY_IVANOV, FEDOR_PAVLOV, DMITRY_NIKOLAEV, newClient);
    }

    @Test(expected = DataAccessException.class)
    public void duplicatePhoneNumberCreate() throws Exception {
        service.create(new Client(null, "NewName", "NewLastName",
                9532147854L, null, null));
    }

    @Test
    public void delete() throws Exception {
        service.delete(DMITRY_NIKOLAEV_ID);
        assertMatch(service.getAll(), ANDREY_IVANOV, FEDOR_PAVLOV);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        Client client = service.get(DMITRY_NIKOLAEV_ID);
        assertMatch(client, DMITRY_NIKOLAEV);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void update() throws Exception {
        Client updated = new Client(DMITRY_NIKOLAEV);
        updated.setName("UpdatedName");
        service.update(updated);
        assertMatch(service.get(DMITRY_NIKOLAEV_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Client> all = service.getAll();
        assertMatch(all, ANDREY_IVANOV, FEDOR_PAVLOV, DMITRY_NIKOLAEV);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new Client(ANDREY_IVANOV_ID, "   ", "Ivanov",
                        98745663214L, null, null)),
                ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Client(ANDREY_IVANOV_ID, "Andrey", "   ",
                        98745663214L, null, null)),
                ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Client(ANDREY_IVANOV_ID, "Andrey", "Ivanov",
                        null, null, null)),
                ConstraintViolationException.class);
    }
}
