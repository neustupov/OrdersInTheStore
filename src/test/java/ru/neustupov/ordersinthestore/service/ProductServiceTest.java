package ru.neustupov.ordersinthestore.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.neustupov.ordersinthestore.model.Product;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.neustupov.ordersinthestore.BrandTestData.SAMSUNG;
import static ru.neustupov.ordersinthestore.ModelTestData.F;
import static ru.neustupov.ordersinthestore.PriceRequestTestData.PRICE_REQUEST_ONE;
import static ru.neustupov.ordersinthestore.ProductTestData.*;
import static ru.neustupov.ordersinthestore.TypeTestData.TV;
import static ru.neustupov.ordersinthestore.ProductTestData.assertMatch;

public class ProductServiceTest extends AbstractServiceTest{

    @Autowired
    ProductService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("products").clear();
    }

    @Test
    public void create() throws Exception {
        Product newProduct = new Product(null, PRICE_REQUEST_ONE,
                LocalDate.of(2018, 1, 10), 10000, TV, F, SAMSUNG);
        Product created = service.create(newProduct);
        newProduct.setId(created.getId());
        assertMatch(service.getAll(), TV_SAMSUNG_40F6101, WM_LG_10B81, TV_AKAI_21D210, MOUSE_RAZER_MAXIMA,
                newProduct);
    }

    @Test
    public void delete() throws Exception {
        service.delete(TV_SAMSUNG_40F6101_ID);
        assertMatch(service.getAll(), WM_LG_10B81, TV_AKAI_21D210, MOUSE_RAZER_MAXIMA);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        Product product = service.get(TV_AKAI_21D210_ID);
        assertMatch(product, TV_AKAI_21D210);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void update() throws Exception {
        Product updated = new Product(WM_LG_10B81);
        updated.setPrice(100500);
        service.update(updated);
        assertMatch(service.get(WM_LG_10B81_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Product> all = service.getAll();
        assertMatch(all, TV_SAMSUNG_40F6101, WM_LG_10B81, TV_AKAI_21D210, MOUSE_RAZER_MAXIMA);
    }
}
