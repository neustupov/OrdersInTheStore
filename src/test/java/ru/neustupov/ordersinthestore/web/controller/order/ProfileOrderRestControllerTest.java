package ru.neustupov.ordersinthestore.web.controller.order;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.ordersinthestore.TestUtil;
import ru.neustupov.ordersinthestore.model.Order;
import ru.neustupov.ordersinthestore.web.AbstractControllerTest;
import ru.neustupov.ordersinthestore.web.json.JsonUtil;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.ordersinthestore.ClientTestData.ANDREY_IVANOV;
import static ru.neustupov.ordersinthestore.OrderTestData.*;
import static ru.neustupov.ordersinthestore.OrderTestData.ORDER_FIVE;
import static ru.neustupov.ordersinthestore.OrderTestData.ORDER_SIX;
import static ru.neustupov.ordersinthestore.TestUtil.userHttpBasic;
import static ru.neustupov.ordersinthestore.UserTestData.MANAGER;
import static ru.neustupov.ordersinthestore.UserTestData.SELLER;

public class ProfileOrderRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileOrderRestController.REST_URL + '/';

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + ORDER_ONE_ID)
                .with(userHttpBasic(MANAGER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(orderService.getAll(), ORDER_TWO, ORDER_THREE, ORDER_FOUR, ORDER_FIVE, ORDER_SIX);
    }

    @Test
    public void testUpdate() throws Exception {
        Order updated = new Order(ORDER_ONE);
        updated.setReady(false);
        mockMvc.perform(put(REST_URL + ORDER_ONE_ID)
                .with(userHttpBasic(MANAGER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(orderService.get(ORDER_ONE_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Order expected = new Order(null, ANDREY_IVANOV,
                LocalDateTime.of(2018, 9, 10, 10, 0, 0), 1000,
                10000, true, null);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(MANAGER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Order returned = TestUtil.readFromJson(action, Order.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(orderService.getAll(), ORDER_ONE, ORDER_TWO, ORDER_THREE, ORDER_FOUR,
                ORDER_FIVE, ORDER_SIX, expected);
    }

    @Test
    public void testUpdateAccessIsDenied() throws Exception {
        Order updated = new Order(ORDER_ONE);
        updated.setReady(false);
        mockMvc.perform(put(REST_URL + ORDER_ONE_ID)
                .with(userHttpBasic(SELLER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteAccessIsDenied() throws Exception {
        mockMvc.perform(delete(REST_URL + ORDER_ONE_ID)
                .with(userHttpBasic(SELLER)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    public void testCreate500AccessIsDenied() throws Exception {
        Order expected = new Order(null, ANDREY_IVANOV,
                LocalDateTime.of(2018, 7, 10, 10, 0, 0), 1000,
                10000, true, null);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(SELLER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Order returned = TestUtil.readFromJson(action, Order.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(orderService.getAll(), ORDER_ONE, ORDER_TWO, ORDER_THREE, ORDER_FOUR,
                ORDER_FIVE, ORDER_SIX, expected);
    }
}
