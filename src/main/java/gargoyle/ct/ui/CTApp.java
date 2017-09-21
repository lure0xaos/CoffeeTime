package gargoyle.ct.ui;

import gargoyle.ct.pref.CTPreferences;

public interface CTApp extends CTControlActions, CTIconProvider {
    CTPreferences getPreferences();
}
