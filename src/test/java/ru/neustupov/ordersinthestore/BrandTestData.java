package ru.neustupov.ordersinthestore;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.neustupov.ordersinthestore.model.Brand;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.neustupov.ordersinthestore.model.AbstractBaseEntity.START_SEQ;
import static ru.neustupov.ordersinthestore.web.json.JsonUtil.writeIgnoreProps;

public class BrandTestData {

    public static final int SAMSUNG_ID = START_SEQ + 23;
    public static final int LG_ID = START_SEQ + 24;
    public static final int AKAI_ID = START_SEQ + 25;
    public static final int RAZER_ID = START_SEQ + 26;

    public static final Brand SAMSUNG = new Brand(SAMSUNG_ID, "Samsung");
    public static final Brand LG = new Brand(LG_ID, "LG");
    public static final Brand AKAI = new Brand(AKAI_ID, "Akai");
    public static final Brand RAZER = new Brand(RAZER_ID, "Razer");

    public static void assertMatch(Iterable<Brand> actual, Brand... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Brand> actual, Iterable<Brand> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Brand actual, Brand expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Brand... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), ""));
    }

    public static ResultMatcher contentJson(Brand expected) {
        return content().json(writeIgnoreProps(expected, ""));
    }
}
