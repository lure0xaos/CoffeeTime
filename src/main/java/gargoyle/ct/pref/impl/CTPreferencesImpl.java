package gargoyle.ct.pref.impl;

import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.impl.prop.CTPrefBooleanProperty;
import gargoyle.ct.pref.impl.prop.CTPrefFloatProperty;

public class CTPreferencesImpl extends CTBasePreferences implements CTPreferences {
    private final CTPrefBooleanProperty block;
    private final CTPrefBooleanProperty transparency;
    private final CTPrefFloatProperty transparencyLevel;

    public CTPreferencesImpl(Class<?> clazz) {
        super(clazz);
        block = new CTPrefBooleanProperty(preferences, BLOCK, false);
        transparencyLevel = new CTPrefFloatProperty(preferences, TRANSPARENCY_LEVEL, 0.3f);
        transparency = new CTPrefBooleanProperty(preferences, TRANSPARENCY, true);
    }

    @Override
    public CTPrefBooleanProperty block() {
        return block;
    }

    @Override
    public CTPrefBooleanProperty transparency() {
        return transparency;
    }

    @Override
    public CTPrefFloatProperty transparencyLevel() {
        return transparencyLevel;
    }
}
