package gargoyle.ct.convert;

import gargoyle.ct.convert.impl.BooleanConverter;
import gargoyle.ct.convert.impl.ByteConverter;
import gargoyle.ct.convert.impl.BytesConverter;
import gargoyle.ct.convert.impl.CharConverter;
import gargoyle.ct.convert.impl.DoubleConverter;
import gargoyle.ct.convert.impl.FloatConverter;
import gargoyle.ct.convert.impl.IntegerConverter;
import gargoyle.ct.convert.impl.LongConverter;
import gargoyle.ct.convert.impl.ShortConverter;
import gargoyle.ct.convert.impl.StringConverter;

import java.util.HashMap;
import java.util.Map;

public final class Converters {
    private static final Map<Class<? extends Converter<?>>, Converter<?>> instances = new HashMap<>();
    private static final Map<Class<?>, Class<? extends Converter<?>>> types = new HashMap<>();

    static {
        init();
    }

    private Converters() {
    }

    @SuppressWarnings("unchecked")
    public static <T> Converter<T> get(Class<T> type) {
        if (!types.containsKey(type)) {
            throw new IllegalArgumentException(type.toGenericString());
        }
        Class<? extends Converter<?>> clazz = types.get(type);
        Converter<T> converter = (Converter<T>) instances.get(clazz);
        if (converter != null) {
            return converter;
        }
        try {
            converter = (Converter<T>) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        instances.put(clazz, converter);
        return converter;
    }

    private static void init() {
        addConverterClass(Boolean.class, BooleanConverter.class);
        addConverterClass(Byte.class, ByteConverter.class);
        addConverterClass(byte[].class, BytesConverter.class);
        addConverterClass(Character.class, CharConverter.class);
        addConverterClass(Double.class, DoubleConverter.class);
        addConverterClass(Float.class, FloatConverter.class);
        addConverterClass(Integer.class, IntegerConverter.class);
        addConverterClass(Long.class, LongConverter.class);
        addConverterClass(Short.class, ShortConverter.class);
        addConverterClass(String.class, StringConverter.class);
    }

    private static <T> void addConverterClass(Class<T> type, Class<? extends Converter<T>> converterClass) {
        types.put(type, converterClass);
    }
}
