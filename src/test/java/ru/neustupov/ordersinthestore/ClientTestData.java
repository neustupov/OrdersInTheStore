package ru.neustupov.ordersinthestore;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.neustupov.ordersinthestore.model.Client;
import ru.neustupov.ordersinthestore.web.json.JsonUtil;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.neustupov.ordersinthestore.model.AbstractBaseEntity.START_SEQ;
import static ru.neustupov.ordersinthestore.web.json.JsonUtil.writeIgnoreProps;

public class ClientTestData {

    public static final int ANDREY_IVANOV_ID = START_SEQ + 3;
    public static final int FEDOR_PAVLOV_ID = START_SEQ + 4;
    public static final int DMITRY_NIKOLAEV_ID = START_SEQ + 5;

    public static final Client ANDREY_IVANOV = new Client(ANDREY_IVANOV_ID, "Andrey", "Ivanov",
            98745663214L, null, null);
    public static final Client FEDOR_PAVLOV = new Client(FEDOR_PAVLOV_ID, "Fedor", "Pavlov",
            9114525874L, null, null);
    public static final Client DMITRY_NIKOLAEV = new Client(DMITRY_NIKOLAEV_ID, "Dmitry", "Nikolaev",
            9532147854L, null, null);

    public static void assertMatch(Iterable<Client> actual, Client... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Client actual, Client expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "priceRequests", "orders",
                "phoneNumber");
    }

    public static void assertMatch(Iterable<Client> actual, Iterable<Client> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("priceRequests", "orders",
                "phoneNumber").isEqualTo(expected);
    }
}
