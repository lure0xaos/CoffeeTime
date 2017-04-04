package gargoyle.ct.pref;

import gargoyle.ct.ui.CTApp;
import gargoyle.ct.util.Log;
import sun.util.logging.PlatformLogger;
import sun.util.logging.PlatformLogger.Level;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class CTPreferencesImpl implements CTPreferences {

    static {
        PlatformLogger.getLogger("java.util.prefs").setLevel(Level.OFF);
    }

    private final Preferences prefs;

    public CTPreferencesImpl(CTApp app) {
        prefs = Preferences.userNodeForPackage(app.getClass());
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
        return prefs.getBoolean(TRANSPARENCY_ENABLED, true);
    }

    @Override
    public void setTransparencyEnabled(boolean transparencyEnabled) {
        prefs.putBoolean(TRANSPARENCY_ENABLED, transparencyEnabled);
        sync();
    }

    @Override
    public float getTransparency() {
        return prefs.getFloat(TRANSPARENCY, 0.3f);
    }

    @Override
    public void setTransparency(float transparency) {
        prefs.putFloat(TRANSPARENCY, transparency);
        sync();
    }
}
