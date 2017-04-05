package gargoyle.ct.pref;

public interface CTPreferences extends CTPreferencesManager {
    float getTransparencyLevel();

    void setTransparencyLevel(float transparencyLevel);

    boolean isTransparency();

    void setTransparency(boolean transparency);
}
