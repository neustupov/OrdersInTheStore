package ru.neustupov.ordersinthestore.web.controller.priceRequest;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.neustupov.ordersinthestore.model.PriceRequest;
import ru.neustupov.ordersinthestore.web.AbstractControllerTest;
import ru.neustupov.ordersinthestore.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.ordersinthestore.PriceRequestTestData.*;
import static ru.neustupov.ordersinthestore.PriceRequestTestData.PRICE_REQUEST_FOUR;
import static ru.neustupov.ordersinthestore.TestUtil.userHttpBasic;
import static ru.neustupov.ordersinthestore.UserTestData.MANAGER;
import static ru.neustupov.ordersinthestore.UserTestData.SELLER;

public class ProfilePriceRequestRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = ProfilePriceRequestRestController.REST_URL + '/';

    @Test
    public void testUpdate() throws Exception {
        PriceRequest updated = new PriceRequest(PRICE_REQUEST_ONE);
        updated.setReady(false);
        mockMvc.perform(put(REST_URL + PRICE_REQUEST_ONE_ID)
                .with(userHttpBasic(MANAGER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(priceRequestService.get(PRICE_REQUEST_ONE_ID), updated);
    }

    @Test
    public void testUpdate500AccessIsDenied() throws Exception {
        PriceRequest updated = new PriceRequest(PRICE_REQUEST_ONE);
        updated.setReady(false);
        mockMvc.perform(put(REST_URL + PRICE_REQUEST_ONE_ID)
                .with(userHttpBasic(SELLER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + PRICE_REQUEST_ONE_ID)
                .with(userHttpBasic(MANAGER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(priceRequestService.getAll(), PRICE_REQUEST_TWO, PRICE_REQUEST_THREE, PRICE_REQUEST_FOUR);
    }

    @Test
    public void testDelete500AccessIsDenied() throws Exception {
        mockMvc.perform(delete(REST_URL + PRICE_REQUEST_ONE_ID)
                .with(userHttpBasic(SELLER)))
                .andExpect(status().is5xxServerError());
    }
}
