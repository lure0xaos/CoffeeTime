package gargoyle.ct.config.convert.impl;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.convert.CTTimeConverter;

import java.util.concurrent.TimeUnit;

public final class CTConfigConverter implements CTTimeConverter<CTConfig> {
    private static final long serialVersionUID = -4028873834233716689L;

    @Override
    public String format(TimeUnit unit, CTConfig data) {
        return data.format();
    }

    @Override
    public CTConfig parse(String data) {
        return CTConfig.parse(data);
    }
}
