package gargoyle.ct.messages.impl

import gargoyle.ct.messages.LocaleProvider
import gargoyle.ct.pref.CTPreferences.SupportedLocales
import gargoyle.ct.pref.CTPreferences.SupportedLocales.Companion.findSimilar
import gargoyle.ct.prop.CTObservableProperty
import gargoyle.ct.prop.impl.simple.CTSimpleEnumProperty
import java.util.Locale

class CTFixedLocaleProvider @JvmOverloads constructor(
    private val locale: CTObservableProperty<SupportedLocales> = CTSimpleEnumProperty(
        findSimilar()
    )
) : LocaleProvider {
    override fun getLocale(): Locale {
        return locale.get().locale
    }

    override fun setLocale(locale: Locale) {
        this.locale.set(SupportedLocales.forLocale(locale))
    }

    override fun locale(): CTObservableProperty<SupportedLocales> {
        return locale
    }
}
