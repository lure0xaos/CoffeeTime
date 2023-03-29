package gargoyle.ct.preferences

import gargoyle.ct.config.CTConfig
import gargoyle.ct.util.messages.Described
import java.util.*

interface CTPreferences {
    var block: Boolean
    var config: CTConfig
    var iconStyle: IconStyle
    var sound: Boolean
    var supportedLocales: Locale
    var transparency: Boolean
    var transparencyLevel: Int

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
