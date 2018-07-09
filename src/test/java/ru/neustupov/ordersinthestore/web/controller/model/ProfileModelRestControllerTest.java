package ru.neustupov.ordersinthestore.web.controller.model;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.ordersinthestore.TestUtil;
import ru.neustupov.ordersinthestore.model.Model;
import ru.neustupov.ordersinthestore.util.exception.ErrorType;
import ru.neustupov.ordersinthestore.web.AbstractControllerTest;
import ru.neustupov.ordersinthestore.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.ordersinthestore.ModelTestData.*;
import static ru.neustupov.ordersinthestore.ModelTestData.F;
import static ru.neustupov.ordersinthestore.ModelTestData.F_ID;
import static ru.neustupov.ordersinthestore.TestUtil.userHttpBasic;
import static ru.neustupov.ordersinthestore.UserTestData.MANAGER;
import static ru.neustupov.ordersinthestore.UserTestData.SELLER;

public class ProfileModelRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileModelRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + F_ID)
                .with(userHttpBasic(SELLER)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(F));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + F_ID)
                .with(userHttpBasic(MANAGER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(modelService.getAll(), B, D, MAXIMA);
    }

    @Test
    public void testDelete500AccessIsDenied() throws Exception {
        mockMvc.perform(delete(REST_URL + F_ID)
                .with(userHttpBasic(SELLER)))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testUpdate() throws Exception {
        Model updated = new Model(F);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL + F_ID)
                .with(userHttpBasic(MANAGER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(modelService.get(F_ID), updated);
    }

    @Test
    public void testUpdate500AccessIsDenied() throws Exception {
        Model updated = new Model(F);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL + F_ID)
                .with(userHttpBasic(SELLER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testCreate() throws Exception {
        Model expected = new Model(null, "NewModelOne");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(SELLER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Model returned = TestUtil.readFromJson(action, Model.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(modelService.getAll(), F, B, D, MAXIMA, expected);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(SELLER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(F, B, D, MAXIMA)));
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
        Model expected = new Model(null, "");
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
        Model updated = new Model(F);
        updated.setName("");
        mockMvc.perform(put(REST_URL + F_ID)
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
        Model invalid = new Model(F);
        invalid.setName("<script>alert(123)</script>");
        mockMvc.perform(put(REST_URL + F_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(MANAGER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }
}
