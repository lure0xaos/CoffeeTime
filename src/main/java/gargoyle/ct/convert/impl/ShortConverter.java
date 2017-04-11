package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;

public class ShortConverter implements Converter<Short> {
    @Override
    public String format(Short data) {
        return String.valueOf(data);
    }

    @Override
    public Short parse(String data) {
        return Short.valueOf(data);
    }
}
