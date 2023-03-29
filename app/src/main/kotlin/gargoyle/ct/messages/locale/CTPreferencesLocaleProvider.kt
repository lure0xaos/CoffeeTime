package gargoyle.ct.messages.locale

import gargoyle.ct.preferences.CTPreferences
import gargoyle.ct.util.messages.LocaleProvider
import java.util.*
import kotlin.reflect.KMutableProperty0

class CTPreferencesLocaleProvider(localeProperty: KMutableProperty0<Locale>) : LocaleProvider {
    override var locale: Locale by localeProperty

    constructor(preferences: CTPreferences) : this(preferences::supportedLocales)

}
