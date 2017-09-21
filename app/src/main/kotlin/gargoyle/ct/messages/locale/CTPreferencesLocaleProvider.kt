package gargoyle.ct.messages.locale

import gargoyle.ct.preferences.CTPreferences
import gargoyle.ct.util.messages.LocaleProvider
import gargoyle.ct.util.messages.SupportedLocales
import gargoyle.ct.util.prop.CTObservableProperty
import java.util.*

class CTPreferencesLocaleProvider private constructor(
    private val locale: CTObservableProperty<SupportedLocales>
) : LocaleProvider {
    constructor(preferences: CTPreferences) : this(preferences.supportedLocales())

    override fun getLocale(): Locale = locale.get().locale

    override fun setLocale(locale: Locale) {
        SupportedLocales.forLocale(locale)?.let { this.locale.set(it) }
    }

    override fun locale(): CTObservableProperty<SupportedLocales> = locale
}
