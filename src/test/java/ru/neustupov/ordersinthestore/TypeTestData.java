package ru.neustupov.ordersinthestore;

import ru.neustupov.ordersinthestore.model.Type;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.neustupov.ordersinthestore.model.AbstractBaseEntity.START_SEQ;

public class TypeTestData {

    public static final int TV_ID = START_SEQ + 16;
    public static final int WASHING_MACHINE_ID = START_SEQ + 17;
    public static final int MOUSE_ID = START_SEQ + 18;

    public static final Type TV = new Type(TV_ID, "TV");
    public static final Type WASHING_MACHINE = new Type(WASHING_MACHINE_ID, "Washing Machine");
    public static final Type MOUSE = new Type(MOUSE_ID, "Mouse");

    public static void assertMatch(Iterable<Type> actual, Type... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Type> actual, Iterable<Type> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Type actual, Type expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
