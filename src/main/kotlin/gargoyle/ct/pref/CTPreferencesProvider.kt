package gargoyle.ct.pref

import java.util.prefs.Preferences

fun interface CTPreferencesProvider {
    fun preferences(): Preferences
}
