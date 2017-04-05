package gargoyle.ct.pref;

import gargoyle.ct.util.Log;
import sun.util.logging.PlatformLogger;
import sun.util.logging.PlatformLogger.Level;

import java.util.prefs.BackingStoreException;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

public class CTPreferencesImpl implements CTPreferences, CTPreferencesManager {
    private static final String PREF_TRANSPARENCY = "transparency";
    private static final String PREF_TRANSPARENCY_LEVEL = "transparency-level";

    static {
        PlatformLogger.getLogger("java.util.prefs").setLevel(Level.OFF);
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
