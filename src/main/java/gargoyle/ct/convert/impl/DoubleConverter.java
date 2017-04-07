package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;

public class DoubleConverter implements Converter<Double> {
    @Override
    public String format(Double data) {
        return String.valueOf(data);
    }

    @Override
    public Double parse(String data) {
        return Double.valueOf(data);
    }
}
