package gargoyle.ct.pref;

import gargoyle.ct.ui.CTApp;
import gargoyle.ct.util.Log;
import sun.util.logging.PlatformLogger;
import sun.util.logging.PlatformLogger.Level;

import java.util.prefs.BackingStoreException;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

public class CTPreferencesImpl implements CTPreferences, CTPreferencesManager {

    private static final String PREF_TRANSPARENCY_ENABLED = "transparencyEnabled";
    private static final String PREF_TRANSPARENCY = "transparency";

    static {
        PlatformLogger.getLogger("java.util.prefs").setLevel(Level.OFF);
    }

    private final Preferences prefs;

    public CTPreferencesImpl(CTApp app) {
        prefs = Preferences.userNodeForPackage(app.getClass());
    }

    @Override
    public void addPreferenceChangeListener(PreferenceChangeListener pcl) {
        prefs.addPreferenceChangeListener(pcl);
    }

    @Override
    public void removePreferenceChangeListener(PreferenceChangeListener pcl) {
        prefs.removePreferenceChangeListener(pcl);
    }

    private void sync() {
        try {
            prefs.flush();
        } catch (BackingStoreException e) {
            Log.error(e, e.getMessage());
        }
    }

    @Override
    public boolean isTransparencyEnabled() {
        return prefs.getBoolean(PREF_TRANSPARENCY_ENABLED, true);
    }

    @Override
    public void setTransparencyEnabled(boolean transparencyEnabled) {
        prefs.putBoolean(PREF_TRANSPARENCY_ENABLED, transparencyEnabled);
        sync();
    }

    @Override
    public float getTransparency() {
        return prefs.getFloat(PREF_TRANSPARENCY, 0.3f);
    }

    @Override
    public void setTransparency(float transparency) {
        prefs.putFloat(PREF_TRANSPARENCY, transparency);
        sync();
    }
}
