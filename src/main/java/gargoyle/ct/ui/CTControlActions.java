package gargoyle.ct.ui;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.CTConfigs;
import gargoyle.ct.pref.CTPreferences;

import java.awt.*;

public interface CTControlActions {
    void arm(CTConfig config);

    void exit();

    CTConfigs loadConfigs(boolean reload);

    void showPreferences(Window owner, String title);

    void help();

    CTConfig newConfig(Component owner, String title);

    void saveConfigs(CTConfigs configs);

    void unarm();

    CTPreferences preferences();
}
