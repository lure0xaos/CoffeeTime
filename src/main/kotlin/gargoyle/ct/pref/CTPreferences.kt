package gargoyle.ct.pref

import gargoyle.ct.config.CTConfig
import gargoyle.ct.messages.Described
import gargoyle.ct.pref.impl.prop.CTPrefProperty
import java.util.Arrays
import java.util.Locale

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

    enum class SupportedLocales(  //NON-NLS
        val locale: Locale
    ) : Described {
        EN(Locale.ENGLISH), RU(Locale("ru", "RU"));

        override val description: String
            get() = locale.displayName

        companion object {
            @JvmOverloads
            fun findSimilar(locale: Locale = Locale.getDefault(), def: SupportedLocales = EN): SupportedLocales {
                return Arrays.stream(values()).filter { value: SupportedLocales -> isSimilar(value.locale, locale) }
                    .findFirst().orElse(def)
            }

            private fun isSimilar(locale1: Locale, locale2: Locale): Boolean {
                return locale1 == locale2
            }

            fun forLocale(locale: Locale): SupportedLocales {
                return Arrays.stream(values()).filter { value: SupportedLocales? ->
                    isSimilar(
                        value!!.locale, locale
                    )
                }.findFirst().orElse(null)
            }
        }
    }

    companion object {
        const val PREF_BLOCK = "block"
        const val PREF_CONFIG = "config"
        const val PREF_ICON_STYLE = "icon-style"
        const val OPACITY_PERCENT = 100
        const val PREF_SOUND = "sound"
        const val PREF_TRANSPARENCY = "transparency"
        const val PREF_TRANSPARENCY_LEVEL = "transparency-level"
    }
}
