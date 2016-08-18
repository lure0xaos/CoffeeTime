package gargoyle.ct;

import java.util.concurrent.TimeUnit;

public final class CTConfigsDataConverter implements Converter<String[]> {
    private static CTConfigsDataConverter instance;

    public static synchronized CTConfigsDataConverter getInstance() {
        if (CTConfigsDataConverter.instance == null) {
            CTConfigsDataConverter.instance = new CTConfigsDataConverter();
        }
        return CTConfigsDataConverter.instance;
    }

    private CTConfigsDataConverter() {
        super();
    }

    @Override
    public String format(final TimeUnit unit, final String[] data) {
        final StringBuilder ret = new StringBuilder();
        for (final String string : data) {
            ret.append(string).append("\n");
        }
        return ret.toString();
    }

    @Override
    public String[] parse(final String line) {
        final String[] split = line.split("\n");
        return split;
    }
}
