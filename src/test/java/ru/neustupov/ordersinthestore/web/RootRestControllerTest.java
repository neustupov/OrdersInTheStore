package ru.neustupov.ordersinthestore.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.ordersinthestore.TestUtil;
import ru.neustupov.ordersinthestore.UserTestData;
import ru.neustupov.ordersinthestore.model.Role;
import ru.neustupov.ordersinthestore.model.User;
import ru.neustupov.ordersinthestore.web.controller.RootRestController;

import java.time.Instant;
import java.util.Date;
import java.util.EnumSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.ordersinthestore.UserTestData.*;

public class RootRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RootRestController.REST_URL + '/';

    @Test
    public void testRegister() throws Exception {
        User expected = new User(null, "NewRegisterSeller", "newregisterseller@yandex.ru",
                "newPass", Date.from(Instant.now()), EnumSet.of(Role.ROLE_SELLER));
        ResultActions action = mockMvc.perform(post(REST_URL + "register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserTestData.jsonWithPassword(expected, "newPass")))
                .andExpect(status().isCreated());

        User returned = TestUtil.readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(userService.getAll(), ADMIN, MANAGER, expected, SELLER);
    }
}
