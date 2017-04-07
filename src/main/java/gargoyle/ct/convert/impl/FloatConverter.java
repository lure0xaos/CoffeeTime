package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;

public class FloatConverter implements Converter<Float> {
    private static final long serialVersionUID = -3545302235031253063L;

    @Override
    public String format(Float data) {
        return String.valueOf(data);
    }

    @Override
    public Float parse(String data) {
        return Float.valueOf(data);
    }
}
