package gargoyle.ct.prop.impl.simple

import gargoyle.ct.prop.impl.CTBaseObservableProperty
import java.text.MessageFormat
import kotlin.reflect.KClass

abstract class CTSimpleProperty<T : Any> : CTBaseObservableProperty<T> {
    private lateinit var value: T

    @Suppress("UNCHECKED_CAST")
    protected constructor(name: String, def: T) : super(def::class as KClass<T>, name) {
        value = def
    }

    protected constructor(type: KClass<T>, name: String) : super(type, name)

    override fun set(value: T) {
        val oldValue = get()
        if (oldValue == value) {
            return
        }
        this.value = value
        val thread = firePropertyChange(value, oldValue)
        try {
            thread?.join()
        } catch (ex: InterruptedException) {
            throw IllegalStateException(ex)
        }
    }

    override fun get(): T {
        return value
    }

    override fun toString(): String {
        return MessageFormat.format("CTSimpleProperty'{'name=''{0}'', value={1}'}'", name, value)
    }
}
