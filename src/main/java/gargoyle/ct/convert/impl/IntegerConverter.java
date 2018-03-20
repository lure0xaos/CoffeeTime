package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;
import org.jetbrains.annotations.NotNull;

public class IntegerConverter implements Converter<Integer> {
    @Override
    public String format(Integer data) {
        return String.valueOf(data);
    }

    @NotNull
    @Override
    public Integer parse(@NotNull String data) {
        return Integer.valueOf(data);
    }
}
