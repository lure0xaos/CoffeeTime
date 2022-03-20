package gargoyle.ct.prop

import kotlin.reflect.KClass

interface CTReadOnlyProperty<T : Any> {
    fun get(): T
    val isPresent: Boolean
    fun name(): String
    fun type(): KClass<T>
}
