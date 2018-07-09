package ru.neustupov.ordersinthestore.web.controller.client;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.ordersinthestore.TestUtil;
import ru.neustupov.ordersinthestore.model.Client;
import ru.neustupov.ordersinthestore.util.exception.ErrorType;
import ru.neustupov.ordersinthestore.web.AbstractControllerTest;
import ru.neustupov.ordersinthestore.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.ordersinthestore.ClientTestData.*;
import static ru.neustupov.ordersinthestore.ClientTestData.DMITRY_NIKOLAEV;
import static ru.neustupov.ordersinthestore.ClientTestData.DMITRY_NIKOLAEV_ID;
import static ru.neustupov.ordersinthestore.TestUtil.userHttpBasic;
import static ru.neustupov.ordersinthestore.UserTestData.MANAGER;
import static ru.neustupov.ordersinthestore.UserTestData.SELLER;

public class ProfileClientRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileClientRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + ANDREY_IVANOV_ID)
                .with(userHttpBasic(SELLER)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ANDREY_IVANOV));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + ANDREY_IVANOV_ID)
                .with(userHttpBasic(MANAGER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(clientService.getAll(), FEDOR_PAVLOV, DMITRY_NIKOLAEV);
    }

    @Test
    public void testUpdate() throws Exception {
        Client updated = new Client(DMITRY_NIKOLAEV);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL + DMITRY_NIKOLAEV_ID)
                .with(userHttpBasic(MANAGER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(clientService.get(DMITRY_NIKOLAEV_ID), updated);
    }

    @Test
    public void testUpdate500AccessIsDenied() throws Exception {
        Client updated = new Client(DMITRY_NIKOLAEV);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL + DMITRY_NIKOLAEV_ID)
                .with(userHttpBasic(SELLER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testDelete500AccessIsDenied() throws Exception {
        mockMvc.perform(delete(REST_URL + ANDREY_IVANOV_ID)
                .with(userHttpBasic(SELLER)))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testCreate() throws Exception {
        Client expected = new Client(null, "Mike", "Smith",
                3222233223L, null, null);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(MANAGER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Client returned = TestUtil.readFromJson(action, Client.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(clientService.getAll(), ANDREY_IVANOV, FEDOR_PAVLOV, DMITRY_NIKOLAEV, expected);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(SELLER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ANDREY_IVANOV, FEDOR_PAVLOV, DMITRY_NIKOLAEV)));
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
        Client expected = new Client(null, "", "Smith",
                3211233223L, null, null);
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
        Client updated = new Client(DMITRY_NIKOLAEV);
        updated.setName("");
        mockMvc.perform(put(REST_URL + DMITRY_NIKOLAEV_ID)
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
        Client invalid = new Client(DMITRY_NIKOLAEV);
        invalid.setName("<script>alert(123)</script>");
        mockMvc.perform(put(REST_URL + DMITRY_NIKOLAEV_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(MANAGER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }
}
