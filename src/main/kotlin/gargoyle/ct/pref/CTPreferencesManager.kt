package gargoyle.ct.pref

import gargoyle.ct.pref.impl.prop.CTPrefProperty
import kotlin.reflect.KClass

interface CTPreferencesManager {
    fun addPropertyChangeListener(listener: CTPropertyChangeListener<*>)
    fun <T : Any> getProperty(name: String): CTPrefProperty<T>
    fun <E : Enum<E>> getProperty(type: KClass<E>): CTPrefProperty<E>
    val propertyNames: Set<String>
    fun hasProperty(name: String): Boolean
    fun removePropertyChangeListener(listener: CTPropertyChangeListener<*>)
}
