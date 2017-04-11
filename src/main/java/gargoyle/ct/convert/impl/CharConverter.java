package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;

public class CharConverter implements Converter<Character> {
    @Override
    public String format(Character data) {
        return String.valueOf(data);
    }

    @Override
    public Character parse(String data) {
        return data.charAt(0);
    }
}
