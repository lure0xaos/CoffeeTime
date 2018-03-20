package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;
import org.jetbrains.annotations.NotNull;

public class BooleanConverter implements Converter<Boolean> {
    @Override
    public String format(Boolean data) {
        return String.valueOf(data);
    }

    @NotNull
    @Override
    public Boolean parse(String data) {
        return Boolean.valueOf(data);
    }
}
