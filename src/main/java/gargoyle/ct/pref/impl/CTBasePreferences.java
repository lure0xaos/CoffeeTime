package gargoyle.ct.pref.impl;

import gargoyle.ct.log.Log;
import gargoyle.ct.pref.CTPreferencesManager;

import java.lang.reflect.InvocationTargetException;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

abstract class CTBasePreferences implements CTPreferencesManager {
    private static final String MSG_JAVA_UTIL_LOGGING_ERROR = "java.util.logging error";

    static {
        try {
            Class<?> loggerClass = Class.forName("sun.util.logging.PlatformLogger");
            Class<?> levelClass = Class.forName("sun.util.logging.PlatformLogger$Level");
            //noinspection unchecked
            loggerClass.getMethod("setLevel", levelClass)
                    .invoke(loggerClass.getMethod("getLogger", String.class)
                                    .invoke(null, "java.util.preferences"), //NON-NLS
                            Enum.valueOf((Class<Enum>) levelClass, "OFF")); //NON-NLS
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            Log.warn(e, MSG_JAVA_UTIL_LOGGING_ERROR);
        }
    }

    protected final Preferences preferences;

    protected CTBasePreferences(Class<?> clazz) {
        preferences = Preferences.userNodeForPackage(clazz);
    }

    @Override
    public void addPreferenceChangeListener(PreferenceChangeListener pcl) {
        preferences.addPreferenceChangeListener(pcl);
    }

    @Override
    public void removePreferenceChangeListener(PreferenceChangeListener pcl) {
        preferences.removePreferenceChangeListener(pcl);
    }
}
