package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;

public class EnumConverter<E extends Enum<E>> implements Converter<E> {
    private final Class<E> type;

    public EnumConverter(Class<E> type) {
        this.type = type;
    }

    @Override
    public String format(E data) {
        return data.name();
    }

    @Override
    public E parse(String data) {
        return Enum.valueOf(type, data);
    }
}
