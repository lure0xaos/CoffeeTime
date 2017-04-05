package gargoyle.ct.pref;

import java.util.prefs.PreferenceChangeListener;

public interface CTPreferencesManager {
    void addPreferenceChangeListener(PreferenceChangeListener pcl);

    void removePreferenceChangeListener(PreferenceChangeListener pcl);
}
