package gargoyle.ct.util.prop

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class AbstractObservableDelegate<V : Any>(
    private val beforeChange: (KProperty<*>, V, V) -> Boolean = { _: KProperty<*>, _: V, _: V -> true },
    private val afterChange: (KProperty<*>, V, V) -> Unit = { _: KProperty<*>, _: V, _: V -> }
) : ReadWriteProperty<Any?, V> {

    protected abstract fun _getValue(thisRef: Any?, property: KProperty<*>): V

    protected abstract fun _setValue(thisRef: Any?, property: KProperty<*>, value: V)

    final override fun getValue(thisRef: Any?, property: KProperty<*>): V =
        _getValue(thisRef, property)

    final override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        val oldValue = _getValue(thisRef, property)
        if (beforeChange(property, oldValue, value)) {
            _setValue(thisRef, property, value)
            afterChange(property, oldValue, value)
        }
    }

}
