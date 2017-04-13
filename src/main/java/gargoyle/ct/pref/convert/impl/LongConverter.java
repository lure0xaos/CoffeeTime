package gargoyle.ct.pref.convert.impl;

import gargoyle.ct.pref.convert.Converter;

public class LongConverter implements Converter<Long> {
    @Override
    public String format(Long data) {
        return String.valueOf(data);
    }

    @Override
    public Long parse(String data) {
        return Long.valueOf(data);
    }
}
