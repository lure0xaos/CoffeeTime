package gargoyle.ct;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.config.CTConfigs;
import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.impl.prop.CTPrefBooleanProperty;
import gargoyle.ct.pref.impl.prop.CTPrefFloatProperty;
import gargoyle.ct.ui.CTApp;
import gargoyle.ct.ui.impl.CTBlocker;

import java.awt.*;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

public final class CTBlockerTest {
    private CTBlockerTest() {
    }

    public static void main(String[] args) {
        for (CTBlocker blocker : CTBlocker.forAllDevices(new CTApp() {
            @Override
            public void arm(CTConfig config) {
            }

            @Override
            public void exit() {
            }

            @Override
            public void help() {
            }

            @Override
            public CTConfigs loadConfigs(boolean reload) {
                return null;
            }

            @Override
            public CTPreferences preferences() {
                return new CTPreferences() {
                    @Override
                    public void addPreferenceChangeListener(PreferenceChangeListener pcl) {
                    }

                    @Override
                    public void removePreferenceChangeListener(PreferenceChangeListener pcl) {
                    }

                    @Override
                    public CTPrefBooleanProperty block() {
                        return new CTPrefBooleanProperty(Preferences.userNodeForPackage(CTBlockerTest.class), CTPreferences.BLOCK) {
                        };
                    }

                    @Override
                    public CTPrefBooleanProperty transparency() {
                        return null;
                    }

                    @Override
                    public CTPrefFloatProperty transparencyLevel() {
                        return null;
                    }
                };
            }

            @Override
            public void saveConfigs(CTConfigs configs) {
            }

            @Override
            public CTConfig showNewConfig() {
                return null;
            }

            @Override
            public void showPreferences() {
            }

            @Override
            public void unarm() {
            }
        })) {
            blocker.debug(true);
            blocker.showText(Color.WHITE, "00:00");
        }
    }
}
