package ru.neustupov.ordersinthestore;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.neustupov.ordersinthestore.model.PriceRequest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.neustupov.ordersinthestore.ClientTestData.ANDREY_IVANOV;
import static ru.neustupov.ordersinthestore.ClientTestData.DMITRY_NIKOLAEV;
import static ru.neustupov.ordersinthestore.ClientTestData.FEDOR_PAVLOV;
import static ru.neustupov.ordersinthestore.OrderTestData.ORDER_ONE;
import static ru.neustupov.ordersinthestore.OrderTestData.ORDER_THREE;
import static ru.neustupov.ordersinthestore.OrderTestData.ORDER_TWO;
import static ru.neustupov.ordersinthestore.UserTestData.SELLER;
import static ru.neustupov.ordersinthestore.model.AbstractBaseEntity.START_SEQ;
import static ru.neustupov.ordersinthestore.web.json.JsonUtil.writeIgnoreProps;

public class PriceRequestTestData {

    public static final int PRICE_REQUEST_ONE_ID = START_SEQ + 12;
    public static final int PRICE_REQUEST_TWO_ID = START_SEQ + 13;
    public static final int PRICE_REQUEST_THREE_ID = START_SEQ + 14;
    public static final int PRICE_REQUEST_FOUR_ID = START_SEQ + 15;

    public static final PriceRequest PRICE_REQUEST_ONE = new PriceRequest(PRICE_REQUEST_ONE_ID, SELLER,
            LocalDateTime.of(2018, 1, 7, 10, 0, 0 ), ORDER_ONE,
            ANDREY_IVANOV, true);
    public static final PriceRequest PRICE_REQUEST_TWO = new PriceRequest(PRICE_REQUEST_TWO_ID, SELLER,
            LocalDateTime.of(2018, 1, 8, 10, 0, 0 ), ORDER_TWO,
            FEDOR_PAVLOV, true);
    public static final PriceRequest PRICE_REQUEST_THREE = new PriceRequest(PRICE_REQUEST_THREE_ID, SELLER,
            LocalDateTime.of(2018, 1, 9, 10, 0, 0 ), ORDER_THREE,
            DMITRY_NIKOLAEV, true);
    public static final PriceRequest PRICE_REQUEST_FOUR = new PriceRequest(PRICE_REQUEST_FOUR_ID, SELLER,
            LocalDateTime.of(2018, 1, 10, 10, 0, 0 ), null,
            DMITRY_NIKOLAEV, false);

    public static void assertMatch(PriceRequest actual, PriceRequest expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "products");
    }

    public static void assertMatch(Iterable<PriceRequest> actual, PriceRequest... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<PriceRequest> actual, Iterable<PriceRequest> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("products").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(PriceRequest... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "products"));
    }

    public static ResultMatcher contentJson(PriceRequest expected) {
        return content().json(writeIgnoreProps(expected, "product"));
    }
}
