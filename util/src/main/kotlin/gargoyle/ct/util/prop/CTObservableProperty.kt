package gargoyle.ct.util.prop

import gargoyle.ct.util.pref.CTPropertyChangeListener
import java.util.function.Function

interface CTObservableProperty<T : Any> : CTProperty<T> {
    fun addPropertyChangeListener(listener: CTPropertyChangeListener<T>)
    fun <T2 : Any> bind(property: CTProperty<T2>, mapper: Function<T, T2>)
    fun bind(property: CTProperty<T>)
    fun <T2 : Any> bindBi(property: CTObservableProperty<T2>, mapper: Function<T, T2>, mapper2: Function<T2, T>)
    fun bindBi(property: CTObservableProperty<T>)
    fun removePropertyChangeListener(listener: CTPropertyChangeListener<T>)
}
