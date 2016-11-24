package gargoyle.ct.ui;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.CTConfigs;

public interface CTControlActions {

    void arm(CTConfig config);

    void exit();

    CTConfigs getConfigs();

    void help();

    void unarm();
}
