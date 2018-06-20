package ru.neustupov.ordersinthestore.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.neustupov.ordersinthestore.model.Model;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static ru.neustupov.ordersinthestore.ModelTestData.*;

public class ModelServiceTest extends AbstractServiceTest {

    @Autowired
    private ModelService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("models").clear();
    }

    @Test
    public void create() throws Exception {
        Model newModel = new Model(null, "NewBrand");
        Model created = service.create(newModel);
        newModel.setId(created.getId());
        assertMatch(service.getAll(), F, B, D, MAXIMA, newModel);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateNameCreate() throws Exception {
        service.create(new Model(null, "Maxima"));
    }

    @Test
    public void delete() throws Exception {
        service.delete(MAXIMA_ID);
        assertMatch(service.getAll(), F, B, D);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        Model model = service.get(MAXIMA_ID);
        assertMatch(model, MAXIMA);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void update() throws Exception {
        Model updated = new Model(MAXIMA);
        updated.setName("UpdatedName");
        service.update(updated);
        assertMatch(service.get(MAXIMA_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Model> all = service.getAll();
        assertMatch(all, F, B, D, MAXIMA);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new Model(100500, "   ")),
                ConstraintViolationException.class);
    }
}
