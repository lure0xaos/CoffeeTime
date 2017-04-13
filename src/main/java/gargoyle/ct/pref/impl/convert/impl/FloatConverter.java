package gargoyle.ct.pref.impl.convert.impl;

import gargoyle.ct.pref.impl.convert.Converter;

public class FloatConverter implements Converter<Float> {
    @Override
    public String format(Float data) {
        return String.valueOf(data);
    }

    @Override
    public Float parse(String data) {
        return Float.valueOf(data);
    }
}
