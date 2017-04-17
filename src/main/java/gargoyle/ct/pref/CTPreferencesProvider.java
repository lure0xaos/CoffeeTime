package gargoyle.ct.pref;

import java.util.prefs.Preferences;

@FunctionalInterface
public interface CTPreferencesProvider {

    Preferences preferences();
}
