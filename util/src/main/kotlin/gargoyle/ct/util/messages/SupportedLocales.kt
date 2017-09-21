package gargoyle.ct.util.messages

import java.util.*

enum class SupportedLocales(  //NON-NLS
    val locale: Locale
) : Described {
    EN(Locale.ENGLISH), RU(Locale("ru", "RU"));

    override val description: String
        get() = locale.displayName

    companion object {
        fun findSimilar(locale: Locale = Locale.getDefault(), def: SupportedLocales = EN): SupportedLocales =
            values().firstOrNull { value: SupportedLocales -> isSimilar(value.locale, locale) } ?: def

        private fun isSimilar(locale1: Locale, locale2: Locale): Boolean = locale1 == locale2

        fun forLocale(locale: Locale): SupportedLocales? = values().firstOrNull { isSimilar(it.locale, locale) }
    }
}
