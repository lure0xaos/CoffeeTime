package gargoyle.ct.ui;

import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.ver.CTVersionInfo;

public interface CTApp extends CTControlActions, CTIconProvider {
    CTPreferences getPreferences();

    CTVersionInfo getVersionInfo();
}
