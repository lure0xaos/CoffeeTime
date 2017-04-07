package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;

public class BooleanConverter implements Converter<Boolean> {
    @Override
    public String format(Boolean data) {
        return String.valueOf(data);
    }

    @Override
    public Boolean parse(String data) {
        return Boolean.valueOf(data);
    }
}
