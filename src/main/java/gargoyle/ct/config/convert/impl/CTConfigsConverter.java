package gargoyle.ct.config.convert.impl;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.CTConfigs;
import gargoyle.ct.config.convert.CTUnitConverter;
import gargoyle.ct.log.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

public final class CTConfigsConverter implements CTUnitConverter<CTConfigs> {
    private static final String MSG_INVALID_CONVERT_LINE_0 = "skip invalid convert line: {0}";
    private final CTUnitConverter<CTConfig> configConverter = new CTConfigConverter();
    private final CTConfigsDataConverter configsDataConverter = new CTConfigsDataConverter();

    @Override
    public String format(TimeUnit unit, CTConfigs data) {
        List<CTConfig> configs = data.getConfigs();
        int length = configs.size();
        String[] formats = new String[length];
        for (int i = 0; i < length; i++) {
            formats[i] = configConverter.format(unit, configs.get(i));
        }
        return configsDataConverter.format(unit, formats);
    }

    @Override
    public CTConfigs parse(String line) {
        String[] data = configsDataConverter.parse(line);
        int length = data.length;
        CTConfig[] configs = new CTConfig[length];
        for (int i = 0; i < length; i++) {
            try {
                configs[i] = configConverter.parse(data[i]);
            } catch (IllegalArgumentException ex) {
                Log.error(MSG_INVALID_CONVERT_LINE_0, data[i]);
            }
        }
        return new CTConfigs(configs);
    }
}
