package gargoyle.ct.messages.impl

import gargoyle.ct.messages.LocaleProvider
import gargoyle.ct.pref.CTPreferences
import gargoyle.ct.pref.CTPreferences.SupportedLocales
import gargoyle.ct.prop.CTObservableProperty
import java.util.Locale

class CTPreferencesLocaleProvider private constructor(private val locale: CTObservableProperty<SupportedLocales>) :
    LocaleProvider {
    constructor(preferences: CTPreferences) : this(preferences.supportedLocales())

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
