package ru.neustupov.ordersinthestore.web.controller.type;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.ordersinthestore.TestUtil;
import ru.neustupov.ordersinthestore.model.Type;
import ru.neustupov.ordersinthestore.util.exception.ErrorType;
import ru.neustupov.ordersinthestore.web.AbstractControllerTest;
import ru.neustupov.ordersinthestore.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.ordersinthestore.TestUtil.userHttpBasic;
import static ru.neustupov.ordersinthestore.TypeTestData.*;
import static ru.neustupov.ordersinthestore.TypeTestData.WASHING_MACHINE_ID;
import static ru.neustupov.ordersinthestore.UserTestData.MANAGER;
import static ru.neustupov.ordersinthestore.UserTestData.SELLER;

public class ProfileTypeRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileTypeRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + TV_ID)
                .with(userHttpBasic(SELLER)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(TV));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + TV_ID)
                .with(userHttpBasic(MANAGER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(typeService.getAll(), WASHING_MACHINE, MOUSE);
    }

    @Test
    public void testDeleteAccessIsDenied() throws Exception {
        mockMvc.perform(delete(REST_URL + TV_ID)
                .with(userHttpBasic(SELLER)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdate() throws Exception {
        Type updated = new Type(TV);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL + TV_ID)
                .with(userHttpBasic(MANAGER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(typeService.get(TV_ID), updated);
    }

    @Test
    public void testUpdateAccessIsDenied() throws Exception {
        Type updated = new Type(TV);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL + TV_ID)
                .with(userHttpBasic(SELLER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCreate() throws Exception {
        Type expected = new Type(null, "NewTypeOne");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(SELLER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Type returned = TestUtil.readFromJson(action, Type.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(typeService.getAll(), TV, WASHING_MACHINE, MOUSE, expected);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(SELLER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(TV, WASHING_MACHINE, MOUSE)));
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
        Type expected = new Type(null, "");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(MANAGER))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Type updated = new Type(TV);
        updated.setName("");
        mockMvc.perform(put(REST_URL + TV_ID)
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
        Type invalid = new Type(WASHING_MACHINE);
        invalid.setName("<script>alert(123)</script>");
        mockMvc.perform(put(REST_URL + WASHING_MACHINE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(MANAGER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }
}
