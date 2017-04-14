package gargoyle.ct.config.convert.impl;

import gargoyle.ct.config.convert.CTUnitConverter;

import java.util.concurrent.TimeUnit;

public final class CTConfigsDataConverter implements CTUnitConverter<String[]> {

    private static final String NEWLINE = "\n";

    @Override
    public String format(TimeUnit unit, String... data) {
        StringBuilder ret = new StringBuilder();
        for (String string : data) {
            ret.append(string).append(NEWLINE);
        }
        return ret.toString();
    }

    @Override
    public String[] parse(String line) {
        return line.split(NEWLINE);
    }
}
