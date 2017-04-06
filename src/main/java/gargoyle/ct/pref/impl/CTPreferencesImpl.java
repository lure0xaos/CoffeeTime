package gargoyle.ct.pref.impl;

import gargoyle.ct.log.Log;
import gargoyle.ct.pref.CTPreferences;

import java.lang.reflect.InvocationTargetException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

public class CTPreferencesImpl implements CTPreferences {
    private static final String MSG_JAVA_UTIL_LOGGING_ERROR = "java.util.logging error";

    static {
        try {
            Class<?> loggerClass = Class.forName("sun.util.logging.PlatformLogger");
            Class<?> levelClass = Class.forName("sun.util.logging.PlatformLogger$Level");
            //noinspection unchecked
            loggerClass.getMethod("setLevel", levelClass)
                    .invoke(loggerClass.getMethod("getLogger", String.class)
                                    .invoke(null, "java.util.prefs"), //NON-NLS
                            Enum.valueOf((Class<Enum>) levelClass, "OFF")); //NON-NLS
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            Log.warn(e, MSG_JAVA_UTIL_LOGGING_ERROR);
        }
    }

    private final Preferences prefs;

    public CTPreferencesImpl(Class<?> clazz) {
        prefs = Preferences.userNodeForPackage(clazz);
    }

    @Override
    public void addPreferenceChangeListener(PreferenceChangeListener pcl) {
        prefs.addPreferenceChangeListener(pcl);
    }

    @Override
    public void removePreferenceChangeListener(PreferenceChangeListener pcl) {
        prefs.removePreferenceChangeListener(pcl);
    }

    @Override
    public float getTransparencyLevel() {
        return prefs.getFloat(PREF_TRANSPARENCY_LEVEL, 0.3f);
    }

    @Override
    public void setTransparencyLevel(float transparencyLevel) {
        prefs.putFloat(PREF_TRANSPARENCY_LEVEL, transparencyLevel);
        sync();
    }

    @Override
    public boolean isTransparency() {
        return prefs.getBoolean(PREF_TRANSPARENCY, true);
    }

    @Override
    public void setTransparency(boolean transparency) {
        prefs.putBoolean(PREF_TRANSPARENCY, transparency);
        sync();
    }

    private void sync() {
        try {
            prefs.flush();
        } catch (BackingStoreException e) {
            Log.error(e, e.getMessage());
        }
    }
}
