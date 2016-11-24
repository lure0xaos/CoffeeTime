package gargoyle.ct.config.convert;

import gargoyle.ct.config.CTConfigs;
import gargoyle.ct.convert.Converter;

import java.util.concurrent.TimeUnit;

public final class CTConfigsConverter implements Converter<CTConfigs> {

    private static CTConfigsConverter instance;

    private CTConfigsConverter() {
    }

    public static synchronized CTConfigsConverter getInstance() {
        if (instance == null) {
            instance = new CTConfigsConverter();
        }
        return instance;
    }

    @Override
    public String format(TimeUnit unit, CTConfigs data) {
        return data.format();
    }

    @Override
    public CTConfigs parse(String data) {
        return CTConfigs.parse(data);
    }
}
