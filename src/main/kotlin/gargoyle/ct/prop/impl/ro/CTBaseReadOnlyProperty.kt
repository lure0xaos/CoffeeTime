package gargoyle.ct.prop.impl.ro

import gargoyle.ct.prop.CTReadOnlyProperty
import java.text.MessageFormat
import kotlin.reflect.KClass

abstract class CTBaseReadOnlyProperty<T : Any> protected constructor(name: String, value: T) : CTReadOnlyProperty<T> {
    private val name: String
    private val type: KClass<T>
    private val value: T

    init {
        @Suppress("UNCHECKED_CAST")
        type = value::class as KClass<T>
        this.name = name
        this.value = value
    }

    override fun get(): T {
        return value
    }

    override val isPresent: Boolean
        get() = true

    override fun name(): String {
        return name
    }

    override fun type(): KClass<T> {
        return type
    }

    override fun toString(): String {
        return MessageFormat.format("CTBaseReadOnlyProperty'{'name=''{0}'', value={1}'}'", name, value)
    }
}
