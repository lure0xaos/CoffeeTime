package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;
import org.jetbrains.annotations.NotNull;

public class EnumConverter<E extends Enum<E>> implements Converter<E> {
    private final Class<E> type;

    public EnumConverter(Class<E> type) {
        this.type = type;
    }

    @Override
    public String format(@NotNull E data) {
        return data.name();
    }

    @NotNull
    @Override
    public E parse(@NotNull String data) {
        return Enum.valueOf(type, data);
    }
}
