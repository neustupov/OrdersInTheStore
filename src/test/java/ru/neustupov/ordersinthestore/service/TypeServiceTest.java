package ru.neustupov.ordersinthestore.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.neustupov.ordersinthestore.model.Type;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static ru.neustupov.ordersinthestore.TypeTestData.*;
import static ru.neustupov.ordersinthestore.TypeTestData.assertMatch;

public class TypeServiceTest extends AbstractServiceTest {

    @Autowired
    private TypeService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("types").clear();
    }

    @Test
    public void create() throws Exception {
        Type newType = new Type(null, "NewType");
        Type created = service.create(newType);
        newType.setId(created.getId());
        assertMatch(service.getAll(), TV, WASHING_MACHINE, MOUSE, newType);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateNameCreate() throws Exception {
        service.create(new Type(null, "TV"));
    }

    @Test
    public void delete() throws Exception {
        service.delete(TV_ID);
        assertMatch(service.getAll(), WASHING_MACHINE, MOUSE);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        Type type = service.get(TV_ID);
        assertMatch(type, TV);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void update() throws Exception {
        Type updated = new Type(WASHING_MACHINE);
        updated.setName("UpdatedName");
        service.update(updated);
        assertMatch(service.get(WASHING_MACHINE_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Type> all = service.getAll();
        assertMatch(all, TV, WASHING_MACHINE, MOUSE);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new Type(100500, "   ")),
                ConstraintViolationException.class);
    }
}
