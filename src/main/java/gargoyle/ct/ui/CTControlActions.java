package gargoyle.ct.ui;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.CTConfigs;
import org.jetbrains.annotations.Nullable;

public interface CTControlActions {
    void about();

    void arm(CTConfig config);

    void browseConfigs();

    void exit();

    void help();

    CTConfigs loadConfigs(boolean reload);

    void saveConfigs(CTConfigs configs);

    @Nullable CTConfig showNewConfig();

    void showPreferences();

    void unarm();
}
