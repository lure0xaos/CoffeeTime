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
        addProperty(new CTPrefBooleanProperty(this, BLOCK, false));
        CTPrefProperty<Integer> transparencyLevel = new CTPrefIntegerProperty(this, TRANSPARENCY_LEVEL, 30);
        addProperty(transparencyLevel);
        transparencyLevel.addPropertyChangeListener(event -> {
            if (event.getNewValue() < 1) event.getProperty().set(1);
        });
        addProperty(new CTPrefBooleanProperty(this, TRANSPARENCY, true));
        addProperty(new CTPrefEnumProperty<>(this, SUPPORTED_LOCALES.class, SUPPORTED_LOCALES.findSimilar()));
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
