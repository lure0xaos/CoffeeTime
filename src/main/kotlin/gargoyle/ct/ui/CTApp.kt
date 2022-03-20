package gargoyle.ct.ui

import gargoyle.ct.pref.CTPreferences
import gargoyle.ct.ver.CTVersionInfo

interface CTApp : CTControlActions, CTIconProvider {
    val preferences: CTPreferences
    val versionInfo: CTVersionInfo
}
