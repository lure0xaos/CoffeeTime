package gargoyle.ct;

import java.util.concurrent.TimeUnit;

public final class CTConfigConverter implements Converter<CTConfig> {
    private static CTConfigConverter instance;

    public static synchronized CTConfigConverter getInstance() {
        if (CTConfigConverter.instance == null) {
            CTConfigConverter.instance = new CTConfigConverter();
        }
        return CTConfigConverter.instance;
    }

    private CTConfigConverter() {
        super();
    }

    @Override
    public String format(final TimeUnit unit, final CTConfig data) {
        return data.format();
    }

    @Override
    public CTConfig parse(final String data) {
        return CTConfig.parse(data);
    }
}
