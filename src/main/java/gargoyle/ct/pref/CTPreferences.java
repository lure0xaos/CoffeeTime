package gargoyle.ct.pref;

import gargoyle.ct.pref.impl.prop.CTPrefProperty;

public interface CTPreferences extends CTPreferencesManager {
    String BLOCK = "block";
    String TRANSPARENCY = "transparency";
    String TRANSPARENCY_LEVEL = "transparency-level";

    CTPrefProperty<Boolean> block();

    CTPrefProperty<Boolean> transparency();

    CTPrefProperty<Float> transparencyLevel();
}
