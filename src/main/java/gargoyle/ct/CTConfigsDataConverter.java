package gargoyle.ct;

import java.util.concurrent.TimeUnit;

public final class CTConfigsDataConverter implements Converter<String[]> {

    private static CTConfigsDataConverter instance;

    private CTConfigsDataConverter() {
    }

    public static synchronized CTConfigsDataConverter getInstance() {
        if (instance == null) {
            instance = new CTConfigsDataConverter();
        }
        return instance;
    }

    @Override
    public String format(TimeUnit unit, String... data) {
        StringBuilder ret = new StringBuilder();
        for (String string : data) {
            ret.append(string).append("\n");
        }
        return ret.toString();
    }

    @Override
    public String[] parse(String line) {
        return line.split("\n");
    }
}
