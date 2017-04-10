package gargoyle.ct.config.convert.impl;

import gargoyle.ct.config.convert.CTTimeConverter;
import gargoyle.ct.config.data.CTConfig;
import gargoyle.ct.config.data.CTConfigs;
import gargoyle.ct.log.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

public final class CTConfigsConverter implements CTTimeConverter<CTConfigs> {
    private static final String MSG_INVALID_CONVERT_LINE_0 = "skip invalid convert line: {0}";
    private final CTConfigConverter configConverter = new CTConfigConverter();
    private final CTConfigsDataConverter configsDataConverter = new CTConfigsDataConverter();

    @Override
    public String format(TimeUnit unit, CTConfigs data) {
        List<CTConfig> configs = data.getConfigs();
        String[] formats = new String[configs.size()];
        for (int i = 0; i < configs.size(); i++) {
            CTConfig config = configs.get(i);
            formats[i] = configConverter.format(unit, config);
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
