package gargoyle.ct.pref

import gargoyle.ct.prop.CTProperty
import java.text.MessageFormat

class CTPropertyChangeEvent<T : Any>(val property: CTProperty<T>, val name: String, val oldValue: T, val newValue: T) {

    override fun toString(): String {
        return MessageFormat.format("CTPropertyChangeEvent'{'property={0}'}'", property)
    }
}
