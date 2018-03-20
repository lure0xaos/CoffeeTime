package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;
import org.jetbrains.annotations.NotNull;

public class FloatConverter implements Converter<Float> {
    @Override
    public String format(Float data) {
        return String.valueOf(data);
    }

    @NotNull
    @Override
    public Float parse(@NotNull String data) {
        return Float.valueOf(data);
    }
}
