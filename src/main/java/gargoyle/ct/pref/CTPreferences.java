package gargoyle.ct.pref;

public interface CTPreferences {
    String TRANSPARENCY_ENABLED = "transparencyEnabled";
    String TRANSPARENCY = "transparency";

    boolean isTransparencyEnabled();

    void setTransparencyEnabled(boolean transparencyEnabled);

    float getTransparency();

    void setTransparency(float transparency);
}
