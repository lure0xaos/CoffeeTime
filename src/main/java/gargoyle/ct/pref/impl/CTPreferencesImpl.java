package gargoyle.ct.pref.impl;

import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.impl.prop.CTPrefBooleanProperty;
import gargoyle.ct.pref.impl.prop.CTPrefEnumProperty;
import gargoyle.ct.pref.impl.prop.CTPrefIntegerProperty;
import gargoyle.ct.pref.impl.prop.CTPrefProperty;

public class CTPreferencesImpl extends CTBasePreferences implements CTPreferences {
    @SuppressWarnings("MagicNumber")
    public CTPreferencesImpl(Class<?> clazz) {
        super(clazz);
        addProperty(new CTPrefBooleanProperty(preferences, BLOCK, false));
        addProperty(new CTPrefIntegerProperty(preferences, TRANSPARENCY_LEVEL, 30));
        addProperty(new CTPrefBooleanProperty(preferences, TRANSPARENCY, true));
        addProperty(new CTPrefEnumProperty<>(preferences, SUPPORTED_LOCALES.class, SUPPORTED_LOCALES.DEFAULT));
    }

    @Override
    public CTPrefProperty<Boolean> block() {
        return getProperty(BLOCK);
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
}
