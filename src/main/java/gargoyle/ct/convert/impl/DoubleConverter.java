package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;
import org.jetbrains.annotations.NotNull;

public class DoubleConverter implements Converter<Double> {
    @Override
    public String format(Double data) {
        return String.valueOf(data);
    }

    @NotNull
    @Override
    public Double parse(@NotNull String data) {
        return Double.valueOf(data);
    }
}
