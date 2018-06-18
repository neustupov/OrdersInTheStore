package ru.neustupov.oedersinthestore;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.neustupov.ordersinthestore.model.Role;
import ru.neustupov.ordersinthestore.model.User;
import ru.neustupov.ordersinthestore.web.json.JsonUtil;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.neustupov.ordersinthestore.model.AbstractBaseEntity.START_SEQ;
import static ru.neustupov.ordersinthestore.web.json.JsonUtil.writeIgnoreProps;

public class UserTestData {

    public static final int SELLER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MANAGER_ID = START_SEQ + 2;

    public static final User SELLER = new User(SELLER_ID, "Seller", "seller@yandex.ru", "seller", Date.from(Instant.now()), EnumSet.of(Role.ROLE_SELLER));
    public static final User MANAGER = new User(MANAGER_ID, "Manager", "manager@yandex.ru", "manager", Date.from(Instant.now()), EnumSet.of(Role.ROLE_MANAGER));
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@yandex.ru", "admin", Date.from(Instant.now()), EnumSet.of(Role.ROLE_ADMIN));

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles", "priceRequests", "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "priceRequests", "password").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "registered", "password"));
    }

    public static ResultMatcher contentJson(User expected) {
        return content().json(writeIgnoreProps(expected, "registered", "password"));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
