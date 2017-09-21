package gargoyle.ct.util;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

@SuppressWarnings("StaticMethodOnlyUsedInOneClass")
public final class CTNumberUtil {
    private static final String METHOD_VALUE_OF = "valueOf";
    private static final String MSG_CANNOT_CREATE_INSTANCE_OF_0 = "Cannot create instance of {0}";

    private CTNumberUtil() {
    }

    @SuppressWarnings({"unchecked", "JavaReflectionMemberAccess"})
    public static <T extends Number> T fromInt(Class<T> type, int value) {
        try {
            return (T) type.getMethod(METHOD_VALUE_OF, String.class).invoke(null, String.valueOf(value));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object getDefault(Class<?> type) {
        Object def;
        try {
            def = Number.class.isAssignableFrom(type) ?
                    type.getMethod(METHOD_VALUE_OF, String.class).invoke(null, "0") :
                    type.newInstance();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException
                ex) {
            throw new RuntimeException(MessageFormat.format(MSG_CANNOT_CREATE_INSTANCE_OF_0, type), ex);
        }
        return def;
    }

    public static Integer getInteger(Object value) {
        return Integer.valueOf(String.valueOf(value));
    }

    public static Integer toRange(Integer min, Integer max, Integer value) {
        return Math.max(min, Math.min(max, value));
    }

    public static <T extends Number> int toRange(T min, T max, T value) {
        return Math.max(toInt(min), Math.min(toInt(max), toInt(value)));
    }

    public static <T extends Number> int toInt(T value) {
        return Integer.parseInt(String.valueOf(value));
    }
}
