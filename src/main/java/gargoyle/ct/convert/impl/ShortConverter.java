package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;
import org.jetbrains.annotations.NotNull;

public class ShortConverter implements Converter<Short> {
    @Override
    public String format(Short data) {
        return String.valueOf(data);
    }

    @NotNull
    @Override
    public Short parse(@NotNull String data) {
        return Short.valueOf(data);
    }
}
