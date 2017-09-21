package gargoyle.ct.util.args

import gargoyle.ct.util.convert.Converter
import kotlin.reflect.KClass

interface CTAnyArgs {
    operator fun <T : Any> get(type: KClass<T>, key: String): T?
    operator fun <T : Any> get(type: KClass<T>, key: String, def: T): T
    operator fun <T : Any> get(index: Int, def: T): T
    operator fun <T : Any> get(key: String, def: T): T
    operator fun <T : Any> get(index: Int, type: KClass<T>, converter: Converter<T>): T?
    operator fun <T : Any> get(index: Int, type: KClass<T>, converter: Converter<T>, def: T): T
    operator fun <T : Any> get(key: String, type: KClass<T>, converter: Converter<T>): T?
    operator fun <T : Any> get(key: String, type: KClass<T>, converter: Converter<T>, def: T): T
    fun has(index: Int): Boolean
    fun has(key: String): Boolean
    fun size(): Int
}
