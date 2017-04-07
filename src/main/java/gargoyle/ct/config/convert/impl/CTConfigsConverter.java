package gargoyle.ct.config.convert.impl;

import gargoyle.ct.config.CTConfigs;
import gargoyle.ct.config.convert.CTTimeConverter;

import java.util.concurrent.TimeUnit;

public final class CTConfigsConverter implements CTTimeConverter<CTConfigs> {

    @Override
    public String format(TimeUnit unit, CTConfigs data) {
        return data.format();
    }

    @Override
    public CTConfigs parse(String data) {
        return CTConfigs.parse(data);
    }
}
