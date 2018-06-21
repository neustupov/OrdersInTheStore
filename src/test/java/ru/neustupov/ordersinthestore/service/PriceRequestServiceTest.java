package ru.neustupov.ordersinthestore.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.neustupov.ordersinthestore.model.PriceRequest;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

import static ru.neustupov.ordersinthestore.ClientTestData.ANDREY_IVANOV;
import static ru.neustupov.ordersinthestore.OrderTestData.ORDER_ONE;
import static ru.neustupov.ordersinthestore.PriceRequestTestData.*;
import static ru.neustupov.ordersinthestore.UserTestData.SELLER;

public class PriceRequestServiceTest extends AbstractServiceTest {

    @Autowired
    private PriceRequestService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("priceRequests").clear();
    }

    @Test
    public void create() throws Exception {
        PriceRequest newPriceRequest = new PriceRequest(null, SELLER,
                LocalDateTime.of(2018, 1, 7, 13, 0, 0), ORDER_ONE,
                ANDREY_IVANOV, false);
        PriceRequest created = service.create(newPriceRequest);
        newPriceRequest.setId(created.getId());
        assertMatch(service.getAll(), PRICE_REQUEST_ONE, PRICE_REQUEST_TWO, PRICE_REQUEST_THREE,
                PRICE_REQUEST_FOUR, newPriceRequest);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateUserAndAddDateNumberCreate() throws Exception {
        service.create(new PriceRequest(null, SELLER,
                LocalDateTime.of(2018, 1, 7, 10, 0, 0), ORDER_ONE,
                ANDREY_IVANOV, true));
    }

    @Test
    public void delete() throws Exception {
        service.delete(PRICE_REQUEST_ONE_ID);
        assertMatch(service.getAll(), PRICE_REQUEST_TWO, PRICE_REQUEST_THREE, PRICE_REQUEST_FOUR);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        PriceRequest priceRequest = service.get(PRICE_REQUEST_ONE_ID);
        assertMatch(priceRequest, PRICE_REQUEST_ONE);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void update() throws Exception {
        PriceRequest updated = new PriceRequest(PRICE_REQUEST_ONE);
        updated.setReady(false);
        service.update(updated);
        assertMatch(service.get(PRICE_REQUEST_ONE_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<PriceRequest> all = service.getAll();
        assertMatch(all, PRICE_REQUEST_ONE, PRICE_REQUEST_TWO, PRICE_REQUEST_THREE, PRICE_REQUEST_FOUR);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new PriceRequest(PRICE_REQUEST_ONE_ID, null,
                        LocalDateTime.of(2018, 1, 7, 10, 0, 0), ORDER_ONE,
                        ANDREY_IVANOV, true)),
                ConstraintViolationException.class);
        validateRootCause(() -> service.create(new PriceRequest(PRICE_REQUEST_ONE_ID, SELLER, null, ORDER_ONE,
                ANDREY_IVANOV, true)), ConstraintViolationException.class);
    }
}
