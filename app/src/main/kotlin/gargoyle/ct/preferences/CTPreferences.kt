package gargoyle.ct.preferences

import gargoyle.ct.config.CTConfig
import gargoyle.ct.util.messages.Described
import gargoyle.ct.util.messages.SupportedLocales
import gargoyle.ct.util.pref.CTPreferencesManager
import gargoyle.ct.util.pref.prop.CTPrefProperty

interface CTPreferences : CTPreferencesManager {
    fun block(): CTPrefProperty<Boolean>
    fun config(): CTPrefProperty<CTConfig>
    fun iconStyle(): CTPrefProperty<IconStyle>
    fun sound(): CTPrefProperty<Boolean>
    fun supportedLocales(): CTPrefProperty<SupportedLocales>
    fun transparency(): CTPrefProperty<Boolean>
    fun transparencyLevel(): CTPrefProperty<Int>
    enum class IconStyle(val path: String, override val description: String) : Described {
        BW("bw", "icon-style.bw"), WB("wb", "icon-style.wb");

        override fun toString(): String {
            return description
        }
    }


    companion object {
        const val PREF_BLOCK: String = "block"
        const val PREF_CONFIG: String = "config"
        const val PREF_ICON_STYLE: String = "icon-style"
        const val OPACITY_PERCENT: Int = 100
        const val PREF_SOUND: String = "sound"
        const val PREF_TRANSPARENCY: String = "transparency"
        const val PREF_TRANSPARENCY_LEVEL: String = "transparency-level"
    }
}
