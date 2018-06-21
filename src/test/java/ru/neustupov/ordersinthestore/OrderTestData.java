package ru.neustupov.ordersinthestore;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.neustupov.ordersinthestore.model.Order;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.neustupov.ordersinthestore.ClientTestData.ANDREY_IVANOV;
import static ru.neustupov.ordersinthestore.ClientTestData.DMITRY_NIKOLAEV;
import static ru.neustupov.ordersinthestore.ClientTestData.FEDOR_PAVLOV;
import static ru.neustupov.ordersinthestore.model.AbstractBaseEntity.START_SEQ;
import static ru.neustupov.ordersinthestore.web.json.JsonUtil.writeIgnoreProps;

public class OrderTestData {

    public static final int ORDER_ONE_ID = START_SEQ + 6;
    public static final int ORDER_TWO_ID = START_SEQ + 7;
    public static final int ORDER_THREE_ID = START_SEQ + 8;
    public static final int ORDER_FOUR_ID = START_SEQ + 9;
    public static final int ORDER_FIVE_ID = START_SEQ + 10;
    public static final int ORDER_SIX_ID = START_SEQ + 11;

    public static final Order ORDER_ONE = new Order(ORDER_ONE_ID, ANDREY_IVANOV,
            LocalDateTime.of(2018, 1, 10, 10, 0, 0 ), 1000,
            10000, true, null);
    public static final Order ORDER_TWO = new Order(ORDER_TWO_ID, FEDOR_PAVLOV,
            LocalDateTime.of(2018, 1, 11, 10, 0, 0 ), 500,
            5000, true, null);
    public static final Order ORDER_THREE = new Order(ORDER_THREE_ID, DMITRY_NIKOLAEV,
            LocalDateTime.of(2018, 1, 12, 10, 0, 0 ),0,
            7000, true, null);
    public static final Order ORDER_FOUR = new Order(ORDER_FOUR_ID, ANDREY_IVANOV,
            LocalDateTime.of(2018, 1, 13, 10, 0, 0 ),100,
            8000, false, null);
    public static final Order ORDER_FIVE = new Order(ORDER_FIVE_ID, FEDOR_PAVLOV,
            LocalDateTime.of(2018, 1, 14, 10, 0, 0 ),200,
            5000, false, null);
    public static final Order ORDER_SIX = new Order(ORDER_SIX_ID, DMITRY_NIKOLAEV,
            LocalDateTime.of(2018, 1, 15, 10, 0, 0 ), 1000,
            4000, false, null);

    public static void assertMatch(Order actual, Order expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "priceRequests", "client");
    }

    public static void assertMatch(Iterable<Order> actual, Order... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Order> actual, Iterable<Order> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("priceRequests", "client").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Order... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "priseRequests", "client"));
    }

    public static ResultMatcher contentJson(Order expected) {
        return content().json(writeIgnoreProps(expected, "priceRequests", "client"));
    }
}
