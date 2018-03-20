package gargoyle.ct.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Objects;

@SuppressWarnings("StaticMethodOnlyUsedInOneClass")
public final class Defend {
    private Defend() {
    }

    public static <N> void equals(N actual, N expected, @NotNull String message, Object... arguments) {
        if (!Objects.equals(actual, expected)) {
            fail(message);
        }
    }

    private static void fail(@NotNull String message, Object... arguments) {
        throw new IllegalArgumentException(arguments.length == 0 ? message : MessageFormat.format(message, arguments));
    }

    public static <N extends Number> void inRange(N value, N minimum, N maximum, @NotNull String message, Object... arguments) {
        if (new BigDecimal(String.valueOf(minimum)).compareTo(new BigDecimal(String.valueOf(value))) > 0 ||
                new BigDecimal(String.valueOf(maximum)).compareTo(new BigDecimal(String.valueOf(value))) < 0) {
            fail(message);
        }
    }

    public static <N extends Comparable<N>> void inRange(@NotNull N value, N minimum, @NotNull N maximum, @NotNull String message, Object... arguments) {
        if (minimum.compareTo(value) > 0 || maximum.compareTo(value) < 0) {
            fail(message);
        }
    }

    public static void isFalse(boolean condition, @NotNull String message, Object... arguments) {
        if (condition) {
            fail(message);
        }
    }

    public static void isTrue(boolean condition, @NotNull String message, Object... arguments) {
        if (!condition) {
            fail(message);
        }
    }

    public static void notEmpty(@Nullable CharSequence value, @NotNull String message, Object... arguments) {
        if (value == null || value.length() == 0) {
            fail(message);
        }
    }

    public static void notEmptyTrimmed(@Nullable String value, @NotNull String message, Object... arguments) {
        if (value == null || value.isEmpty() || value.trim().isEmpty()) {
            fail(message);
        }
    }

    public static <T> void instanceOf(T value, Class<? extends T> type, @NotNull String message, Object... arguments) {
        if (!type.isInstance(value)) {
            fail(message, arguments);
        }
    }

    public static <T> void notNull(@Nullable T value, @NotNull String message, Object... arguments) {
        if (value == null) {
            fail(message);
        }
    }

    public static <T> void numeric(@NotNull String value, @NotNull String message, Object... arguments) {
        try {
            notNull(new BigDecimal(value), message);
        } catch (NumberFormatException e) {
            fail(message, arguments);
        }
    }
}
