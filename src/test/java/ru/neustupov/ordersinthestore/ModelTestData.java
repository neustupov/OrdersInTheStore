package ru.neustupov.ordersinthestore;

import ru.neustupov.ordersinthestore.model.Model;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.neustupov.ordersinthestore.model.AbstractBaseEntity.START_SEQ;

public class ModelTestData {

    public static final int F_ID = START_SEQ + 19;
    public static final int B_ID = START_SEQ + 20;
    public static final int D_ID = START_SEQ + 21;
    public static final int MAXIMA_ID = START_SEQ + 22;

    public static final Model F = new Model(F_ID, "40F6101");
    public static final Model B = new Model(B_ID, "10B81");
    public static final Model D = new Model(D_ID, "21D210");
    public static final Model MAXIMA = new Model(MAXIMA_ID, "Maxima");

    public static void assertMatch(Iterable<Model> actual, Model... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Model> actual, Iterable<Model> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Model actual, Model expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
