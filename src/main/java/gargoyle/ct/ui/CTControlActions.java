package gargoyle.ct.ui;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.CTConfigs;

public interface CTControlActions {

    void arm(CTConfig config);

    void exit();

    void help();

    CTConfigs loadConfigs(boolean reload);

    void saveConfigs(CTConfigs configs);

    CTConfig showNewConfig();

    void showPreferences();

    void unarm();
}
