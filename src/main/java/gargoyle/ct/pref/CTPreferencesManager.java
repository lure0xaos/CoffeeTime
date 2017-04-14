package gargoyle.ct.pref;

public interface CTPreferencesManager {

    void addPropertyChangeListener(CTPropertyChangeListener listener);

    void removePropertyChangeListener(CTPropertyChangeListener listener);
}
