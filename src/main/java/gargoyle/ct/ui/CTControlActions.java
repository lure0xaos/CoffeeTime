package gargoyle.ct.ui;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.CTConfigs;

import java.awt.*;

public interface CTControlActions {

    void arm(CTConfig config);

    void exit();

    CTConfigs loadConfigs(boolean reload);

    void help();

    CTConfig newConfig(Component owner, String title);

    void saveConfigs(CTConfigs configs);

    void unarm();
}
