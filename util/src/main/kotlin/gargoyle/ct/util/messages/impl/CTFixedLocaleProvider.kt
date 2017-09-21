package gargoyle.ct.util.messages.impl

import gargoyle.ct.util.messages.LocaleProvider
import gargoyle.ct.util.messages.SupportedLocales
import gargoyle.ct.util.messages.SupportedLocales.Companion.findSimilar
import gargoyle.ct.util.prop.CTObservableProperty
import gargoyle.ct.util.prop.impl.simple.CTSimpleEnumProperty
import java.util.*

class CTFixedLocaleProvider constructor(
    private val locale: CTObservableProperty<SupportedLocales> = CTSimpleEnumProperty(findSimilar())
) : LocaleProvider {
    override fun getLocale(): Locale = locale.get().locale

    override fun setLocale(locale: Locale) {
        SupportedLocales.forLocale(locale)?.let { this.locale.set(it) }
    }

    override fun locale(): CTObservableProperty<SupportedLocales> = locale
}
