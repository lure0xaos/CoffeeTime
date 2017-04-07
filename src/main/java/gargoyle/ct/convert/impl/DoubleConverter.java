package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;

public class DoubleConverter implements Converter<Double> {
    private static final long serialVersionUID = 2145308179021883260L;

    @Override
    public String format(Double data) {
        return String.valueOf(data);
    }

    @Override
    public Double parse(String data) {
        return Double.valueOf(data);
    }
}
