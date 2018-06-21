package ru.neustupov.ordersinthestore;

import ru.neustupov.ordersinthestore.model.Product;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.neustupov.ordersinthestore.BrandTestData.*;
import static ru.neustupov.ordersinthestore.ModelTestData.*;
import static ru.neustupov.ordersinthestore.PriceRequestTestData.PRICE_REQUEST_ONE;
import static ru.neustupov.ordersinthestore.PriceRequestTestData.PRICE_REQUEST_THREE;
import static ru.neustupov.ordersinthestore.PriceRequestTestData.PRICE_REQUEST_TWO;
import static ru.neustupov.ordersinthestore.TypeTestData.MOUSE;
import static ru.neustupov.ordersinthestore.TypeTestData.TV;
import static ru.neustupov.ordersinthestore.TypeTestData.WASHING_MACHINE;
import static ru.neustupov.ordersinthestore.model.AbstractBaseEntity.START_SEQ;

public class ProductTestData {

    public static final int TV_SAMSUNG_40F6101_ID = START_SEQ + 27;
    public static final int WM_LG_10B81_ID = START_SEQ + 28;
    public static final int TV_AKAI_21D210_ID = START_SEQ + 29;
    public static final int MOUSE_RAZER_MAXIMA_ID = START_SEQ + 30;

    public static final Product TV_SAMSUNG_40F6101 = new Product(TV_SAMSUNG_40F6101_ID, PRICE_REQUEST_ONE,
            LocalDate.of(2018, 1, 7), 10000, TV, F, SAMSUNG);
    public static final Product WM_LG_10B81 = new Product(WM_LG_10B81_ID, PRICE_REQUEST_TWO,
            LocalDate.of(2018, 1, 8), 5000, WASHING_MACHINE, B, LG);
    public static final Product TV_AKAI_21D210 = new Product(TV_AKAI_21D210_ID, PRICE_REQUEST_THREE,
            LocalDate.of(2018, 1, 9), 3000, TV, D, AKAI);
    public static final Product MOUSE_RAZER_MAXIMA = new Product(MOUSE_RAZER_MAXIMA_ID, PRICE_REQUEST_THREE,
            LocalDate.of(2018, 1, 9), 4000, MOUSE, MAXIMA, RAZER);

    public static void assertMatch(Product actual, Product expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Product> actual, Product... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Product> actual, Iterable<Product> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
