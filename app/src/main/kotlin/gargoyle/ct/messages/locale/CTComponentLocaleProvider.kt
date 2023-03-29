package gargoyle.ct.messages.locale

import gargoyle.ct.util.messages.LocaleProvider
import gargoyle.ct.util.prop.PropertyObservableDelegate
import java.awt.Component
import java.util.*

class CTComponentLocaleProvider(private val component: Component) : LocaleProvider {

    private var componentLocale: Locale
        get() = component.locale
        set(value) {
            component.locale = value
        }

    override var locale: Locale by PropertyObservableDelegate(this::componentLocale)
}
