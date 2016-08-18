package gargoyle.ct;

import java.util.concurrent.TimeUnit;

public final class CTConfigsConverter implements Converter<CTConfigs> {
    private static CTConfigsConverter instance;

    public static synchronized CTConfigsConverter getInstance() {
        if (CTConfigsConverter.instance == null) {
            CTConfigsConverter.instance = new CTConfigsConverter();
        }
        return CTConfigsConverter.instance;
    }

    private CTConfigsConverter() {
        super();
    }

    @Override
    public String format(final TimeUnit unit, final CTConfigs data) {
        return data.format();
    }

    @Override
    public CTConfigs parse(final String data) {
        return CTConfigs.parse(data);
    }
}
