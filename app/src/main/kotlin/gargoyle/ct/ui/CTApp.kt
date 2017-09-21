package gargoyle.ct.ui

import gargoyle.ct.preferences.CTPreferences
import gargoyle.ct.util.ver.CTVersionInfo

interface CTApp : CTControlActions, CTIconProvider {
    val preferences: CTPreferences
    val versionInfo: CTVersionInfo
}
