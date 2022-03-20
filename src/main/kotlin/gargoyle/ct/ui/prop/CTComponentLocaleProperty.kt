package gargoyle.ct.ui.prop

import gargoyle.ct.pref.CTPreferences.SupportedLocales
import gargoyle.ct.pref.CTPreferences.SupportedLocales.Companion.findSimilar
import gargoyle.ct.prop.impl.CTBaseObservableProperty
import java.awt.Component

class CTComponentLocaleProperty(private val component: Component) : CTBaseObservableProperty<SupportedLocales>(
    SupportedLocales::class, component.name
) {
    override fun get(): SupportedLocales {
        return findSimilar(component.locale, findSimilar())
    }

    override fun set(value: SupportedLocales) {
        component.locale = value.locale
    }
}
