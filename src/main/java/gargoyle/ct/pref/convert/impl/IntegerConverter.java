package gargoyle.ct.pref.convert.impl;

import gargoyle.ct.pref.convert.Converter;

public class IntegerConverter implements Converter<Integer> {
    @Override
    public String format(Integer data) {
        return String.valueOf(data);
    }

    @Override
    public Integer parse(String data) {
        return Integer.valueOf(data);
    }
}
