package gargoyle.ct.pref.impl.convert.impl;

import gargoyle.ct.pref.impl.convert.Converter;

public class StringConverter implements Converter<String> {

    @Override
    public String format(String data) {
        return data;
    }

    @Override
    public String parse(String data) {
        return data;
    }
}
