package gargoyle.ct.util.util

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T, U, V> composeProperties(
    prop: ReadOnlyProperty<T, U>,
    f: (U) -> ReadOnlyProperty<T, V>
): ReadOnlyProperty<T, V> {
    val props = mutableMapOf<Pair<T, KProperty<*>>, ReadOnlyProperty<T, V>>()
    return ReadOnlyProperty { thisRef, property ->
        props.getOrPut(Pair(thisRef, property)) { f(prop.getValue(thisRef, property)) }.getValue(thisRef, property)
    }
}
