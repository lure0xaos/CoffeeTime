package gargoyle.ct.config.convert.impl;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.convert.CTUnitConverter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public final class CTConfigConverter implements CTUnitConverter<CTConfig> {
    private final CTConfigDataConverter configDataConverter = new CTConfigDataConverter();

    @Override
    public String format(TimeUnit unit, CTConfig data) {
        return configDataConverter.format(TimeUnit.MINUTES, data.getWhole(), data.getBlock(), data.getWarn());
    }

    @Override
    public CTConfig parse(@NotNull String line) {
        long[] data = configDataConverter.parse(line);
        return new CTConfig(data[0], data[1], data[2]);
    }
}
