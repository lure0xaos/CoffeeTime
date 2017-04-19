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

    private static final String EQ = "=";
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
        return byIndex(index, new BooleanConverter(), def);
    }

    @Override
    public boolean getBoolean(String key, boolean def) {
        return byKey(key, new BooleanConverter(), def);
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
        return byIndex(index, new ByteConverter(), def);
    }

    @Override
    public byte getByte(String key, byte def) {
        return byKey(key, new ByteConverter(), def);
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
        return byIndex(index, new BytesConverter(), def);
    }

    @Override
    public byte[] getBytes(String key, byte[] def) {
        return byKey(key, new BytesConverter(), def);
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
        return byIndex(index, new CharConverter(), def);
    }

    @Override
    public char getChar(String key, char def) {
        return byKey(key, new CharConverter(), def);// NON-NLS
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
        return byIndex(index, new DoubleConverter(), def);
    }

    @Override
    public double getDouble(String key, double def) {
        return byKey(key, new DoubleConverter(), def);
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
        return byIndex(index, new FloatConverter(), def);
    }

    @Override
    public float getFloat(String key, float def) {
        return byKey(key, new FloatConverter(), def);
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
        return byIndex(index, new IntegerConverter(), def);
    }

    @Override
    public int getInteger(String key, int def) {
        return byKey(key, new IntegerConverter(), def);
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
        return byIndex(index, new LongConverter(), def);
    }

    @Override
    public long getLong(String key, long def) {
        return byKey(key, new LongConverter(), def);
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
        return byIndex(index, new ShortConverter(), def);
    }

    @Override
    public short getShort(String key, short def) {
        return byKey(key, new ShortConverter(), def);
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
        return byIndex(index, new StringConverter(), def);
    }

    @Override
    public String getString(String key, String def) {
        return byKey(key, new StringConverter(), def);
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

    private <T> T byKey(String key, Converter<T> converter, T def) {
        return params.containsKey(key) ? converter.parse(params.get(key)) : def;
    }
}
