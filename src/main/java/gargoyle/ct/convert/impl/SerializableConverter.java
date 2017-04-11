package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;
import gargoyle.ct.util.CTSerializationUtil;

import java.io.IOException;
import java.io.Serializable;

public class SerializableConverter<T extends Serializable> implements Converter<T> {
    private final BytesConverter converter = new BytesConverter();

    @Override
    public String format(T data) {
        try {
            return converter.format(CTSerializationUtil.serialize(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T parse(String data) {
        try {
            return CTSerializationUtil.deserialize(converter.parse(data));
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
