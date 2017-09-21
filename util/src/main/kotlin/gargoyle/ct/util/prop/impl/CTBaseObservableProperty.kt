package gargoyle.ct.util.prop.impl

import gargoyle.ct.util.pref.CTPropertyChangeEvent
import gargoyle.ct.util.pref.CTPropertyChangeListener
import gargoyle.ct.util.prop.CTObservableProperty
import gargoyle.ct.util.prop.CTProperty
import java.util.function.Function
import kotlin.reflect.KClass

abstract class CTBaseObservableProperty<T : Any> protected constructor(type: KClass<T>, name: String) :
    CTBaseProperty<T>(type, name), CTObservableProperty<T> {
    protected fun firePropertyChange(value: T, oldValue: T?): Thread? {
        return if (oldValue == value) {
            null
        } else CTPropertyChangeManager.instance
            .firePropertyChange(
                this,
                CTPropertyChangeEvent(this, name(), oldValue, value)
            )
    }

    override fun <T2 : Any> bindBi(
        property: CTObservableProperty<T2>,
        mapper: Function<T, T2>,
        mapper2: Function<T2, T>
    ) {
        bind(property, mapper)
        property.bind(this, mapper2)
    }

    override fun <T2 : Any> bind(property: CTProperty<T2>, mapper: Function<T, T2>) {
        val myValue = get()
        val otherValue = property.get()
        val newOtherValue = mapper.apply(myValue)
        if (otherValue != newOtherValue) {
            property.set(newOtherValue)
        }
        addPropertyChangeListener { event -> onLateBind(property, mapper, event.newValue) }
    }

    fun <T2 : Any> onLateBind(property: CTProperty<T2>, mapper: Function<T, T2>, newValue: T) {
        property.set(mapper.apply(newValue))
    }

    override fun bindBi(property: CTObservableProperty<T>) {
        bind(property)
        property.bind(this)
    }

    override fun bind(property: CTProperty<T>) {
        val myValue = get()
        val otherValue = property.get()
        if (myValue != otherValue) {
            onLateBind(property, myValue)
        }
        addPropertyChangeListener { event -> onLateBind(property, event.newValue) }
    }

    fun onLateBind(property: CTProperty<T>, newValue: T) {
        property.set(newValue)
    }

    override fun addPropertyChangeListener(listener: CTPropertyChangeListener<T>) {
        CTPropertyChangeManager.instance.addPropertyChangeListener(this, listener)
    }

    override fun removePropertyChangeListener(listener: CTPropertyChangeListener<T>) {
        CTPropertyChangeManager.instance.removePropertyChangeListener(this, listener)
    }
}
