package gargoyle.ct.util.pref.impl

import gargoyle.ct.util.pref.CTPreferencesManager
import gargoyle.ct.util.pref.CTPreferencesProvider
import gargoyle.ct.util.pref.CTPropertyChangeListener
import gargoyle.ct.util.pref.prop.CTPrefProperty
import gargoyle.ct.util.prop.impl.CTPropertyChangeManager
import java.util.prefs.Preferences
import kotlin.reflect.KClass

abstract class CTBasePreferences protected constructor(clazz: KClass<*>) : CTPreferencesManager, CTPreferencesProvider {
    private val preferences: Preferences
    private val properties: MutableMap<String, CTPrefProperty<*>> = mutableMapOf()

    init {
        preferences = Preferences.userNodeForPackage(clazz.java)
    }

    protected fun <T : Any> addProperty(property: CTPrefProperty<T>) {
        properties[property.name()] = property
    }

    override fun addPropertyChangeListener(listener: CTPropertyChangeListener<*>) {
        for (property in properties.values) {
            @Suppress("UNCHECKED_CAST")
            CTPropertyChangeManager.instance.addPropertyChangeListener(
                property as CTPrefProperty<Any>,
                listener as CTPropertyChangeListener<Any>
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getProperty(name: String): CTPrefProperty<T> {
        return properties[name] as CTPrefProperty<T>
    }

    @Suppress("UNCHECKED_CAST")
    override fun <E : Enum<E>> getProperty(type: KClass<E>): CTPrefProperty<E> {
        return properties[type.simpleName] as CTPrefProperty<E>
    }

    override val propertyNames: Set<String>
        get() = properties.keys

    override fun hasProperty(name: String): Boolean {
        return properties.containsKey(name)
    }

    override fun removePropertyChangeListener(listener: CTPropertyChangeListener<*>) {
        for (property in properties.values) {
            @Suppress("UNCHECKED_CAST")
            CTPropertyChangeManager.instance.removePropertyChangeListener(
                property as CTPrefProperty<Any>,
                listener as CTPropertyChangeListener<Any>
            )
        }
    }

    override fun preferences(): Preferences {
        return preferences
    }
}
