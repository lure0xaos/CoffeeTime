package gargoyle.ct.ui;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.CTConfigs;
import gargoyle.ct.pref.CTPreferences;

import java.awt.*;

public interface CTControlActions {
    void arm(CTConfig config);

    void exit();

    void help();

    CTConfigs loadConfigs(boolean reload);

    CTConfig newConfig(Window owner);

    CTPreferences preferences();

    void saveConfigs(CTConfigs configs);

    void showPreferences(Window owner, String title);

    void unarm();
}
