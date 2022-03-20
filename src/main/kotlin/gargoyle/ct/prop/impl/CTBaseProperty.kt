package gargoyle.ct.prop.impl

import gargoyle.ct.prop.CTProperty
import java.text.MessageFormat
import kotlin.reflect.KClass

abstract class CTBaseProperty<T : Any> protected constructor(type: KClass<T>, name: String) : CTProperty<T> {
    protected val name: String
    private val type: KClass<T>

    init {
        this.type = type
        this.name = name
    }

    open operator fun get(def: T): T {
        return get()
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
        return MessageFormat.format("CTBaseProperty'{'name=''{0}'''}'", name)
    }
}
