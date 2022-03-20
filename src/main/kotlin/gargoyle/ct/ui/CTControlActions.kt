package gargoyle.ct.ui

import gargoyle.ct.config.CTConfig
import gargoyle.ct.config.CTConfigs

interface CTControlActions {
    fun about()
    fun arm(config: CTConfig)
    fun browseConfigs()
    fun exit()
    fun help()
    fun loadConfigs(reload: Boolean): CTConfigs
    fun saveConfigs(configs: CTConfigs)
    fun showNewConfig(): CTConfig?
    fun showPreferences()
    fun unarm()
}
