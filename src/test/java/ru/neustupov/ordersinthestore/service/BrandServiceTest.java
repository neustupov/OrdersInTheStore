package ru.neustupov.ordersinthestore.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.neustupov.ordersinthestore.model.Brand;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static ru.neustupov.ordersinthestore.BrandTestData.*;

public class BrandServiceTest extends AbstractServiceTest{

    @Autowired
    private BrandService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("brands").clear();
    }

    @Test
    public void create() throws Exception {
        Brand newBrand = new Brand(null, "NewBrand");
        Brand created = service.create(newBrand);
        newBrand.setId(created.getId());
        assertMatch(service.getAll(), SAMSUNG, LG, AKAI, RAZER, newBrand);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateNameCreate() throws Exception {
        service.create(new Brand(null, "LG"));
    }

    @Test
    public void delete() throws Exception {
        service.delete(SAMSUNG_ID);
        assertMatch(service.getAll(), LG, AKAI, RAZER);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        Brand brand = service.get(SAMSUNG_ID);
        assertMatch(brand, SAMSUNG);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void update() throws Exception {
        Brand updated = new Brand(AKAI);
        updated.setName("UpdatedName");
        service.update(updated);
        assertMatch(service.get(AKAI_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Brand> all = service.getAll();
        assertMatch(all, SAMSUNG, LG, AKAI, RAZER);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new Brand(100500, "   ")),
                ConstraintViolationException.class);
    }
}
