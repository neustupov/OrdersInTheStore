package ru.neustupov.ordersinthestore.web.json;

import org.junit.Assert;
import org.junit.Test;
import ru.neustupov.ordersinthestore.UserTestData;
import ru.neustupov.ordersinthestore.model.User;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static ru.neustupov.ordersinthestore.UserTestData.*;

public class JsonUtilTest {

    @Test
    public void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(SELLER);
        System.out.println(json);
        User user = JsonUtil.readValue(json, User.class);
        assertMatch(user, SELLER);
    }

    @Test
    public void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(USERS);
        System.out.println(json);
        List<User> users = JsonUtil.readValues(json, User.class);
        assertMatch(users, USERS);
    }

    @Test
    public void testWriteOnlyAccess() throws Exception {
        String json = JsonUtil.writeValue(UserTestData.SELLER);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.SELLER, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        Assert.assertEquals(user.getPassword(), "newPass");
    }
}