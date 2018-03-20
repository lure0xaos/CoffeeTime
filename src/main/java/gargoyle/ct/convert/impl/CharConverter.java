package gargoyle.ct.convert.impl;

import gargoyle.ct.convert.Converter;
import org.jetbrains.annotations.NotNull;

public class CharConverter implements Converter<Character> {
    @Override
    public String format(Character data) {
        return String.valueOf(data);
    }

    @Override
    public Character parse(@NotNull String data) {
        return data.charAt(0);
    }
}
