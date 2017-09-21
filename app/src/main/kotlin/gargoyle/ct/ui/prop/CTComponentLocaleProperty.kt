package gargoyle.ct.ui.prop

import gargoyle.ct.util.messages.SupportedLocales
import gargoyle.ct.util.messages.SupportedLocales.Companion.findSimilar
import gargoyle.ct.util.prop.impl.CTBaseObservableProperty
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
