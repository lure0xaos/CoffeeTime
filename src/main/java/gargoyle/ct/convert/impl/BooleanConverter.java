package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;

public class BooleanConverter implements Converter<Boolean> {
    private static final long serialVersionUID = 1971515431899502941L;

    @Override
    public String format(Boolean data) {
        return String.valueOf(data);
    }

    @Override
    public Boolean parse(String data) {
        return Boolean.valueOf(data);
    }
}
