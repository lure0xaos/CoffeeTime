package gargoyle.ct.pref;

import gargoyle.ct.pref.impl.prop.CTPrefBooleanProperty;
import gargoyle.ct.pref.impl.prop.CTPrefFloatProperty;

public interface CTPreferences extends CTPreferencesManager {
    String PREF_TRANSPARENCY = "pref.transparency";
    String PREF_TRANSPARENCY_LEVEL = "pref.transparency-level";

    CTPrefBooleanProperty transparency();

    CTPrefFloatProperty transparencyLevel();
}
