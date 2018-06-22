package ru.neustupov.ordersinthestore.web.product;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.ordersinthestore.TestUtil;
import ru.neustupov.ordersinthestore.model.Product;
import ru.neustupov.ordersinthestore.util.exception.ErrorType;
import ru.neustupov.ordersinthestore.web.AbstractControllerTest;
import ru.neustupov.ordersinthestore.web.json.JsonUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.ordersinthestore.BrandTestData.SAMSUNG;
import static ru.neustupov.ordersinthestore.ModelTestData.F;
import static ru.neustupov.ordersinthestore.PriceRequestTestData.PRICE_REQUEST_ONE;
import static ru.neustupov.ordersinthestore.ProductTestData.*;
import static ru.neustupov.ordersinthestore.TestUtil.userHttpBasic;
import static ru.neustupov.ordersinthestore.TypeTestData.TV;
import static ru.neustupov.ordersinthestore.UserTestData.ADMIN;
import static ru.neustupov.ordersinthestore.UserTestData.SELLER;

public class AdminProductRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = AdminProductRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + TV_SAMSUNG_40F6101_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(TV_SAMSUNG_40F6101));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + TV_SAMSUNG_40F6101_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(productService.getAll(), WM_LG_10B81, TV_AKAI_21D210, MOUSE_RAZER_MAXIMA);
    }

    @Test
    public void testUpdate() throws Exception {
        Product updated = new Product(TV_AKAI_21D210);
        updated.setPrice(100500);
        mockMvc.perform(put(REST_URL + TV_AKAI_21D210_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(productService.get(TV_AKAI_21D210_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Product expected = new Product(null, PRICE_REQUEST_ONE,
                LocalDate.of(2018, 1, 7), 10000, TV, F, SAMSUNG);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Product returned = TestUtil.readFromJson(action, Product.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(productService.getAll(), TV_SAMSUNG_40F6101, WM_LG_10B81, TV_AKAI_21D210,
                MOUSE_RAZER_MAXIMA, expected);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson( TV_SAMSUNG_40F6101, WM_LG_10B81, TV_AKAI_21D210,
                        MOUSE_RAZER_MAXIMA)));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(SELLER)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testCreateInvalid() throws Exception {
        Product expected = new Product(null, PRICE_REQUEST_ONE,
                null, 10000, TV, F, SAMSUNG);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }
}
