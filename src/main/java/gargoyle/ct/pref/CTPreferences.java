package gargoyle.ct.pref;

public interface CTPreferences extends CTPreferencesManager {
    String PREF_TRANSPARENCY = "pref.transparency";
    String PREF_TRANSPARENCY_LEVEL = "pref.transparency-level";

    float getTransparencyLevel();

    void setTransparencyLevel(float transparencyLevel);

    boolean isTransparency();

    void setTransparency(boolean transparency);
}
