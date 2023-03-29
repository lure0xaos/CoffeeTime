package gargoyle.ct.util.pref

import gargoyle.ct.util.prop.AbstractObservableDelegate
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.util.prefs.Preferences
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

@OptIn(InternalSerializationApi::class)
class PreferencesDelegate<V : Any>(
    defaultValue: V,
    private val preferences: Preferences,
    @Suppress("UNCHECKED_CAST")
    private val serializer: KSerializer<V> = defaultValue::class.serializer() as KSerializer<V>
) : AbstractObservableDelegate<V>() {
    constructor(
        source: KClass<*>,
        defaultValue: V,
        @Suppress("UNCHECKED_CAST")
        serializer: KSerializer<V> = defaultValue::class.serializer() as KSerializer<V>
    ) : this(defaultValue, Preferences.userNodeForPackage(source.java), serializer)

    private val defaultValueStr: String = Json.encodeToString(serializer, defaultValue)

    override fun _getValue(thisRef: Any?, property: KProperty<*>): V =
        Json.decodeFromString(serializer, preferences.get(property.name, defaultValueStr))

    override fun _setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        preferences.put(property.name, Json.encodeToString(serializer, value))
        preferences.flush()
    }
}
