package gargoyle.ct.config.convert.impl;

import gargoyle.ct.config.convert.CTTimeConverter;

import java.util.concurrent.TimeUnit;

public final class CTConfigsDataConverter implements CTTimeConverter<String[]> {
    @Override
    public String format(TimeUnit unit, String... data) {
        StringBuilder ret = new StringBuilder();
        for (String string : data) {
            ret.append(string).append('\n');
        }
        return ret.toString();
    }

    @Override
    public String[] parse(String line) {
        return line.split("\n");
    }
}
