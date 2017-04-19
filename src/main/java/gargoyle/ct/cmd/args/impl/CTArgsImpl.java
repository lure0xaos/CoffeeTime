package gargoyle.ct.cmd.args.impl;

import gargoyle.ct.cmd.args.CTArgs;
import gargoyle.ct.convert.Converter;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CTArgsImpl implements CTArgs {

    private static final String                                           EQ                 = "=";
    private static final Map<Class<?>, Class<? extends Converter<?>>>     converterTypes     = new HashMap<>();
    private static final Map<Class<? extends Converter<?>>, Converter<?>> converterInstances = new HashMap<>();

    static {
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

    private final Map<String, String> params;
    private final List<String>        keys;

    public CTArgsImpl(String[] args) {
        Map<String, String> params = new HashMap<>();
        List<String>        keys   = new ArrayList<>();
        init(args, params, keys);
        this.params = Collections.unmodifiableMap(params);
        this.keys = Collections.unmodifiableList(keys);
    }

    @SuppressWarnings("TypeMayBeWeakened")
    private static void init(String[] args, Map<String, String> params, List<String> keys) {
        if (args != null) {
            for (String arg : args) {
                if (arg != null) {
                    if (arg.contains(EQ)) {
                        String[] pair = arg.split(EQ, 2);
                        String   key  = pair[0].trim();
                        params.put(key, pair[1].trim());
                        keys.add(key);
                    } else {
                        String value = arg.trim();
                        params.put(value, value);
                        keys.add(value);
                    }
                }
            }
        }
    }

    private static <T> void addConverterClass(Class<T> type, Class<? extends Converter<T>> converterClass) {
        converterTypes.put(type, converterClass);
    }

    @Override
    public <T> T get(Class<T> type, String key) {
        return byKey(key, getConverter(type), null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(int index, T def) {
        return byIndex(index, getConverter((Class<T>) def.getClass()), def);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key, T def) {
        return byKey(key, getConverter((Class<T>) def.getClass()), def);
    }

    @Override
    public boolean getBoolean(int index) {
        return getBoolean(index, false);
    }

    @Override
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    @Override
    public boolean getBoolean(int index, boolean def) {
        return byIndex(index, getConverter(Boolean.class), def);
    }

    @Override
    public boolean getBoolean(String key, boolean def) {
        return byKey(key, getConverter(Boolean.class), def);
    }

    @Override
    public byte getByte(int index) {
        return getByte(index, (byte) 0);
    }

    @Override
    public byte getByte(String key) {
        return getByte(key, (byte) 0);
    }

    @Override
    public byte getByte(int index, byte def) {
        return byIndex(index, getConverter(Byte.class), def);
    }

    @Override
    public byte getByte(String key, byte def) {
        return byKey(key, getConverter(Byte.class), def);
    }

    @Override
    public byte[] getBytes(int index) {
        return getBytes(index, new byte[0]);
    }

    @Override
    public byte[] getBytes(String key) {
        return getBytes(key, new byte[0]);
    }

    @Override
    public byte[] getBytes(int index, byte[] def) {
        return byIndex(index, getConverter(byte[].class), def);
    }

    @Override
    public byte[] getBytes(String key, byte[] def) {
        return byKey(key, getConverter(byte[].class), def);
    }

    @SuppressWarnings("MagicCharacter")
    @Override
    public char getChar(int index) {
        return getChar(index, '\0');
    }

    @SuppressWarnings("MagicCharacter")
    @Override
    public char getChar(String key) {
        return getChar(key, '\0');
    }

    @Override
    public char getChar(int index, char def) {
        return byIndex(index, getConverter(Character.class), def);
    }

    @Override
    public char getChar(String key, char def) {
        return byKey(key, getConverter(Character.class), def);// NON-NLS
    }

    @Override
    public double getDouble(int index) {
        return getDouble(index, 0d);
    }

    @Override
    public double getDouble(String key) {
        return getDouble(key, 0d);
    }

    @Override
    public double getDouble(int index, double def) {
        return byIndex(index, getConverter(Double.class), def);
    }

    @Override
    public double getDouble(String key, double def) {
        return byKey(key, getConverter(Double.class), def);
    }

    @Override
    public float getFloat(int index) {
        return getFloat(index, 0f);
    }

    @Override
    public float getFloat(String key) {
        return getFloat(key, 0f);
    }

    @Override
    public float getFloat(int index, float def) {
        return byIndex(index, getConverter(Float.class), def);
    }

    @Override
    public float getFloat(String key, float def) {
        return byKey(key, getConverter(Float.class), def);
    }

    @Override
    public int getInteger(int index) {
        return getInteger(index, 0);
    }

    @Override
    public int getInteger(String key) {
        return getInteger(key, 0);
    }

    @Override
    public int getInteger(int index, int def) {
        return byIndex(index, getConverter(Integer.class), def);
    }

    @Override
    public int getInteger(String key, int def) {
        return byKey(key, getConverter(Integer.class), def);
    }

    @Override
    public long getLong(int index) {
        return getLong(index, 0L);
    }

    @Override
    public long getLong(String key) {
        return getLong(key, 0L);
    }

    @Override
    public long getLong(int index, long def) {
        return byIndex(index, getConverter(Long.class), def);
    }

    @Override
    public long getLong(String key, long def) {
        return byKey(key, getConverter(Long.class), def);
    }

    @Override
    public short getShort(int index) {
        return getShort(index, (short) 0);
    }

    @Override
    public short getShort(String key) {
        return getShort(key, (short) 0);
    }

    @Override
    public short getShort(int index, short def) {
        return byIndex(index, getConverter(Short.class), def);
    }

    @Override
    public short getShort(String key, short def) {
        return byKey(key, getConverter(Short.class), def);
    }

    @Override
    public String getString(int index) {
        return getString(index, "");
    }

    @Override
    public String getString(String key) {
        return getString(key, "");
    }

    @Override
    public String getString(int index, String def) {
        return byIndex(index, getConverter(String.class), def);
    }

    @Override
    public String getString(String key, String def) {
        return byKey(key, getConverter(String.class), def);
    }

    @Override
    public boolean hasArg(int index) {
        return params.size() > index;
    }

    @Override
    public boolean hasArg(String key) {
        return params.containsKey(key);
    }

    @Override
    public int size() {
        return params.size();
    }

    private <T> T byIndex(int index, Converter<T> converter, T def) {
        return params.size() > index ? byKey(keys.get(index), converter, def) : def;
    }

    @SuppressWarnings("unchecked")
    private static synchronized <T> Converter<T> getConverter(Class<T> type) {
        if (!converterTypes.containsKey(type)) {
            throw new IllegalArgumentException(type.toGenericString());
        }
        Class<? extends Converter<?>> converterClass = converterTypes.get(type);
        if (!converterInstances.containsKey(converterClass)) {
            try {
                converterInstances.put(converterClass, converterClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return (Converter<T>) converterInstances.get(converterClass);
    }

    private <T> T byKey(String key, Converter<T> converter, T def) {
        return params.containsKey(key) ? converter.parse(params.get(key)) : def;
    }
}
