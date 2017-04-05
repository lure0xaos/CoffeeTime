package gargoyle.ct.convert.impl;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.convert.Converter;

import java.util.concurrent.TimeUnit;

public final class CTConfigConverter implements Converter<CTConfig> {
    private static CTConfigConverter instance;

    private CTConfigConverter() {
    }

    public static synchronized CTConfigConverter getInstance() {
        if (instance == null) {
            instance = new CTConfigConverter();
        }
        return instance;
    }

    @Override
    public String format(TimeUnit unit, CTConfig data) {
        return data.format();
    }

    @Override
    public CTConfig parse(String data) {
        return CTConfig.parse(data);
    }
}
