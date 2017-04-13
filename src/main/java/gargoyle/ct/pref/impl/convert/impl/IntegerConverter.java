package gargoyle.ct.pref.impl.convert.impl;

import gargoyle.ct.pref.impl.convert.Converter;

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
