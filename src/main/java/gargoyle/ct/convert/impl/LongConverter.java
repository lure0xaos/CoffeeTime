package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;
import org.jetbrains.annotations.NotNull;

public class LongConverter implements Converter<Long> {
    @Override
    public String format(Long data) {
        return String.valueOf(data);
    }

    @NotNull
    @Override
    public Long parse(@NotNull String data) {
        return Long.valueOf(data);
    }
}
