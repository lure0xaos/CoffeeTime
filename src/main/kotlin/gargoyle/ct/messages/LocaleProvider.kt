package gargoyle.ct.messages

import gargoyle.ct.pref.CTPreferences.SupportedLocales
import gargoyle.ct.prop.CTObservableProperty
import java.util.Locale

interface LocaleProvider {
    fun getLocale(): Locale
    fun setLocale(locale: Locale)
    fun locale(): CTObservableProperty<SupportedLocales>
}
