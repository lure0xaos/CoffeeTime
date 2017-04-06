package gargoyle.ct.ui;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.CTConfigs;
import gargoyle.ct.pref.CTPreferences;

public interface CTControlActions {
    void arm(CTConfig config);

    void exit();

    void help();

    CTConfigs loadConfigs(boolean reload);

    CTPreferences preferences();

    void saveConfigs(CTConfigs configs);

    CTConfig showNewConfig();

    void showPreferences();

    void unarm();
}
