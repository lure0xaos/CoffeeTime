package gargoyle.ct.messages.locale

import gargoyle.ct.ui.prop.CTComponentLocaleProperty
import gargoyle.ct.util.messages.LocaleProvider
import gargoyle.ct.util.messages.SupportedLocales
import gargoyle.ct.util.prop.CTObservableProperty
import java.awt.Component
import java.util.*

class CTComponentLocaleProvider private constructor(private val locale: CTObservableProperty<SupportedLocales>) :
    LocaleProvider {
    constructor(component: Component) : this(CTComponentLocaleProperty(component))

    override fun getLocale(): Locale = locale.get().locale

    override fun setLocale(locale: Locale) {
        SupportedLocales.forLocale(locale)?.let { this.locale.set(it) }
    }

    override fun locale(): CTObservableProperty<SupportedLocales> = locale
}
