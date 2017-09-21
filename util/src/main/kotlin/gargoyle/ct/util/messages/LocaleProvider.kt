package gargoyle.ct.util.messages

import gargoyle.ct.util.prop.CTObservableProperty
import java.util.*

interface LocaleProvider {
    fun getLocale(): Locale
    fun setLocale(locale: Locale)
    fun locale(): CTObservableProperty<SupportedLocales>
}
