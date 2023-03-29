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
class PreferencesObservableDelegate<V : Any>(
    defaultValue: V,
    private val preferences: Preferences,
    @Suppress("UNCHECKED_CAST")
    private val serializer: KSerializer<V> = defaultValue::class.serializer() as KSerializer<V>,
    beforeChange: (KProperty<*>, V, V) -> Boolean = { _: KProperty<*>, _: V, _: V -> true },
    afterChange: (KProperty<*>, V, V) -> Unit = { _: KProperty<*>, _: V, _: V -> }
) : AbstractObservableDelegate<V>(beforeChange, afterChange) {
    constructor(
        source: KClass<*>,
        defaultValue: V,
        @Suppress("UNCHECKED_CAST")
        serializer: KSerializer<V> = defaultValue::class.serializer() as KSerializer<V>,
        beforeChange: (KProperty<*>, V, V) -> Boolean = { _: KProperty<*>, _: V, _: V -> true },
        afterChange: (KProperty<*>, V, V) -> Unit = { _: KProperty<*>, _: V, _: V -> }
    ) : this(defaultValue, Preferences.userNodeForPackage(source.java), serializer, beforeChange, afterChange)

    private val defaultValueStr: String = Json.encodeToString(serializer, defaultValue)

    override fun _getValue(thisRef: Any?, property: KProperty<*>): V =
        Json.decodeFromString(serializer, preferences.get(property.name, defaultValueStr))

    override fun _setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        preferences.put(property.name, Json.encodeToString(serializer, value))
        preferences.flush()
    }

}
