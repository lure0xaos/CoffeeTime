package gargoyle.ct.pref.impl;

import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.impl.prop.CTPrefBooleanProperty;
import gargoyle.ct.pref.impl.prop.CTPrefFloatProperty;
import gargoyle.ct.pref.impl.prop.CTPrefProperty;

public class CTPreferencesImpl extends CTBasePreferences implements CTPreferences {
    public CTPreferencesImpl(Class<?> clazz) {
        super(clazz);
        addProperty(new CTPrefBooleanProperty(preferences, BLOCK, false));
        addProperty(new CTPrefFloatProperty(preferences, TRANSPARENCY_LEVEL, 0.3f));
        addProperty(new CTPrefBooleanProperty(preferences, TRANSPARENCY, true));
    }

    @Override
    public CTPrefProperty<Boolean> block() {
        return getProperty(BLOCK);
    }

    @Override
    public CTPrefProperty<Boolean> transparency() {
        return getProperty(TRANSPARENCY);
    }

    @Override
    public CTPrefProperty<Float> transparencyLevel() {
        return getProperty(TRANSPARENCY_LEVEL);
    }
}
