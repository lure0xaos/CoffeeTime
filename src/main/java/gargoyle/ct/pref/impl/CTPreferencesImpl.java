package gargoyle.ct.pref.impl;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.convert.impl.CTConfigConverter;
import gargoyle.ct.convert.Converter;
import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.impl.prop.CTPrefBooleanProperty;
import gargoyle.ct.pref.impl.prop.CTPrefEnumProperty;
import gargoyle.ct.pref.impl.prop.CTPrefIntegerProperty;
import gargoyle.ct.pref.impl.prop.CTPrefObjectProperty;
import gargoyle.ct.pref.impl.prop.CTPrefProperty;

import java.util.concurrent.TimeUnit;

public class CTPreferencesImpl extends CTBasePreferences implements CTPreferences {
    @SuppressWarnings("MagicNumber")
    public CTPreferencesImpl(Class<?> clazz) {
        super(clazz);
        addProperty(new CTPrefBooleanProperty(this, BLOCK, false));
        CTPrefProperty<Integer> transparencyLevel = new CTPrefIntegerProperty(this, TRANSPARENCY_LEVEL, 30);
        addProperty(transparencyLevel);
        transparencyLevel.addPropertyChangeListener(event -> {
            if (event.getNewValue() < 1) event.getProperty().set(1);
        });
        addProperty(new CTPrefEnumProperty<>(this, ICON_STYLE.class, ICON_STYLE_NAME, ICON_STYLE.BW));
        addProperty(new CTPrefBooleanProperty(this, TRANSPARENCY, true));
        addProperty(new CTPrefEnumProperty<>(this, SUPPORTED_LOCALES.class, SUPPORTED_LOCALES.findSimilar()));
        addProperty(new CTPrefObjectProperty<>(CTConfig.class, this, CONFIG, new Converter<CTConfig>() {
            private final CTConfigConverter converter = new CTConfigConverter();

            @Override
            public String format(CTConfig data) {
                return converter.format(TimeUnit.MINUTES, data);
            }

            @Override
            public CTConfig parse(String data) {
                return converter.parse(data);
            }
        }));
    }

    @Override
    public CTPrefProperty<Boolean> block() {
        return getProperty(BLOCK);
    }

    @Override
    public CTPrefProperty<CTConfig> config() {
        return getProperty(CONFIG);
    }

    @Override
    public CTPrefProperty<SUPPORTED_LOCALES> supportedLocales() {
        return getProperty(SUPPORTED_LOCALES.class);
    }

    @Override
    public CTPrefProperty<Boolean> transparency() {
        return getProperty(TRANSPARENCY);
    }

    @Override
    public CTPrefProperty<Integer> transparencyLevel() {
        return getProperty(TRANSPARENCY_LEVEL);
    }

    @Override
    public CTPrefProperty<ICON_STYLE> iconStyle() {
        return getProperty(ICON_STYLE_NAME);
    }
}
