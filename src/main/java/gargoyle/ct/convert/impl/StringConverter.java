package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;

public class StringConverter implements Converter<String> {
    private static final long serialVersionUID = -7716722208962392846L;

    @Override
    public String format(String data) {
        return data;
    }

    @Override
    public String parse(String data) {
        return data;
    }
}
