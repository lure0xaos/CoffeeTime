package gargoyle.ct.ui;

import gargoyle.ct.pref.CTPreferences;

import java.net.URL;

public interface CTApp extends CTControlActions {

    URL getBigIcon();

    URL getMediumIcon();

    URL getSmallIcon();

    CTPreferences getPreferences();
}
