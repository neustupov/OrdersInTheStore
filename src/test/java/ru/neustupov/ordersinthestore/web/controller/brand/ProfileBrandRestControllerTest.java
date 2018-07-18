package ru.neustupov.ordersinthestore.web.controller.brand;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.ordersinthestore.TestUtil;
import ru.neustupov.ordersinthestore.model.Brand;
import ru.neustupov.ordersinthestore.util.exception.ErrorType;
import ru.neustupov.ordersinthestore.web.AbstractControllerTest;
import ru.neustupov.ordersinthestore.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.ordersinthestore.BrandTestData.*;
import static ru.neustupov.ordersinthestore.TestUtil.userHttpBasic;
import static ru.neustupov.ordersinthestore.UserTestData.MANAGER;
import static ru.neustupov.ordersinthestore.UserTestData.SELLER;

public class ProfileBrandRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileBrandRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + LG_ID)
                .with(userHttpBasic(SELLER)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(LG));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + LG_ID)
                .with(userHttpBasic(MANAGER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(brandService.getAll(), SAMSUNG, AKAI, RAZER);
    }

    @Test
    public void testDeleteAccessIsDenied() throws Exception {
        mockMvc.perform(delete(REST_URL + LG_ID)
                .with(userHttpBasic(SELLER)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdate() throws Exception {
        Brand updated = new Brand(SAMSUNG);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL + SAMSUNG_ID)
                .with(userHttpBasic(MANAGER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(brandService.get(SAMSUNG_ID), updated);
    }

    @Test
    public void testUpdateAccessIsDenied() throws Exception {
        Brand updated = new Brand(SAMSUNG);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL + SAMSUNG_ID)
                .with(userHttpBasic(SELLER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCreate() throws Exception {
        Brand expected = new Brand(null, "NewBrandOne");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(SELLER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Brand returned = TestUtil.readFromJson(action, Brand.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(brandService.getAll(), SAMSUNG, LG, AKAI, RAZER, expected);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(SELLER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(SAMSUNG, LG, AKAI, RAZER)));
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
        Brand expected = new Brand(null, "");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(SELLER))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Brand updated = new Brand(SAMSUNG);
        updated.setName("");
        mockMvc.perform(put(REST_URL + SAMSUNG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(MANAGER))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }

    @Test
    public void testUpdateHtmlUnsafe() throws Exception {
        Brand invalid = new Brand(LG);
        invalid.setName("<script>alert(123)</script>");
        mockMvc.perform(put(REST_URL + LG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(MANAGER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }
}
