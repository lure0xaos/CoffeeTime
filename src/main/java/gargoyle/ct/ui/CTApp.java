package gargoyle.ct.ui;

import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.ver.CTVersionInfo;
import org.jetbrains.annotations.NotNull;

public interface CTApp extends CTControlActions, CTIconProvider {
    @NotNull CTPreferences getPreferences();

    @NotNull CTVersionInfo getVersionInfo();
}
