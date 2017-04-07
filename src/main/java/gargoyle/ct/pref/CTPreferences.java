package gargoyle.ct.pref;

import gargoyle.ct.pref.impl.prop.CTPrefBooleanProperty;
import gargoyle.ct.pref.impl.prop.CTPrefFloatProperty;

public interface CTPreferences extends CTPreferencesManager {
    String BLOCK = "block";
    String TRANSPARENCY = "transparency";
    String TRANSPARENCY_LEVEL = "transparency-level";

    CTPrefBooleanProperty block();

    CTPrefBooleanProperty transparency();

    CTPrefFloatProperty transparencyLevel();
}
