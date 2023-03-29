package gargoyle.ct.util.prop

import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

class PropertyObservableDelegate<V : Any>(
    private val prop: KMutableProperty0<V>,
    beforeChange: (KProperty<*>, V, V) -> Boolean = { _: KProperty<*>, _: V, _: V -> true },
    afterChange: (KProperty<*>, V, V) -> Unit = { _: KProperty<*>, _: V, _: V -> }
) : AbstractObservableDelegate<V>(beforeChange, afterChange) {

    override fun _getValue(thisRef: Any?, property: KProperty<*>): V =
        prop.get()

    override fun _setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        this.prop.set(value)
    }

}
