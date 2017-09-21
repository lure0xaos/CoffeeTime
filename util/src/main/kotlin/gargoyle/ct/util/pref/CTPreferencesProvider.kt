package gargoyle.ct.util.pref

import java.util.prefs.Preferences

fun interface CTPreferencesProvider {
    fun preferences(): Preferences
}
