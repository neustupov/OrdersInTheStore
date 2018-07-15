package ru.neustupov.ordersinthestore.web.controller.priceRequest;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.ordersinthestore.TestUtil;
import ru.neustupov.ordersinthestore.model.PriceRequest;
import ru.neustupov.ordersinthestore.util.exception.ErrorType;
import ru.neustupov.ordersinthestore.web.AbstractControllerTest;
import ru.neustupov.ordersinthestore.web.controller.priceRequest.rest.ProfilePriceRequestRestController;
import ru.neustupov.ordersinthestore.web.json.JsonUtil;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.ordersinthestore.ClientTestData.ANDREY_IVANOV;
import static ru.neustupov.ordersinthestore.OrderTestData.ORDER_ONE;
import static ru.neustupov.ordersinthestore.PriceRequestTestData.*;
import static ru.neustupov.ordersinthestore.PriceRequestTestData.PRICE_REQUEST_FOUR;
import static ru.neustupov.ordersinthestore.TestUtil.userHttpBasic;
import static ru.neustupov.ordersinthestore.UserTestData.MANAGER;
import static ru.neustupov.ordersinthestore.UserTestData.SELLER;

public class ProfilePriceRequestRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = ProfilePriceRequestRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + PRICE_REQUEST_ONE_ID)
                .with(userHttpBasic(SELLER)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(PRICE_REQUEST_ONE));
    }

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

    @Test
    public void testCreate() throws Exception {
        PriceRequest expected = new PriceRequest(null, SELLER,
                LocalDateTime.of(2017, 2, 7, 10, 0, 0), ORDER_ONE,
                ANDREY_IVANOV, false);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(SELLER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        PriceRequest returned = TestUtil.readFromJson(action, PriceRequest.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(priceRequestService.getAll(), PRICE_REQUEST_ONE, PRICE_REQUEST_TWO,
                PRICE_REQUEST_THREE, PRICE_REQUEST_FOUR, expected);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(SELLER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson( PRICE_REQUEST_ONE, PRICE_REQUEST_TWO, PRICE_REQUEST_THREE,
                        PRICE_REQUEST_FOUR)));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(SELLER)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(userHttpBasic(MANAGER)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testCreateInvalid() throws Exception {
        PriceRequest expected = new PriceRequest(null, null,
                LocalDateTime.of(2018, 1, 7, 10, 0, 0), ORDER_ONE,
                ANDREY_IVANOV, true);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(SELLER))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }
}
