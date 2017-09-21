package gargoyle.ct.util.args.impl

import gargoyle.ct.util.args.CTArgs
import gargoyle.ct.util.convert.Converter
import gargoyle.ct.util.convert.Converters
import gargoyle.ct.util.log.Log
import kotlin.reflect.KClass

open class CTArgsImpl(args: Array<String>) : CTArgs {
    private val params: LinkedHashMap<String, String>

    init {
        params = args.associateTo(LinkedHashMap()) { arg ->
            if (arg.contains("=")) {
                arg.split("=".toRegex(), 2)
                    .map { it.trim() }
                    .let { it[0] to it[1] }
            } else {
                arg.trim().let { it to it }
            }
        }
    }

    override fun <T : Any> get(type: KClass<T>, key: String): T? = get0(key, Converters[type])

    override fun <T : Any> get(type: KClass<T>, key: String, def: T): T = get(key, type, Converters[type], def)

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(index: Int, def: T): T =
        get(index, def::class as KClass<T>, Converters[def::class as KClass<T>], def)

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(key: String, def: T): T =
        get(key, def::class as KClass<T>, Converters[def::class as KClass<T>], def)

    override fun has(index: Int): Boolean = params.size > index

    override fun has(key: String): Boolean = params.containsKey(key)

    override fun size(): Int = params.size

    override fun <T : Any> get(index: Int, type: KClass<T>, converter: Converter<T>): T? = get0(index, converter)

    override fun <T : Any> get(index: Int, type: KClass<T>, converter: Converter<T>, def: T): T {
        if (index < params.size) {
            params.entries.forEachIndexed { i, (key) ->
                if (i == index)
                    return get(key, type, converter, def)
            }
        }
        return def
    }

    private fun <T : Any> get0(index: Int, converter: Converter<T>): T? {
        if (index < params.size) {
            params.entries.forEachIndexed { i, (key) ->
                if (i == index)
                    return get0(key, converter)
            }
        }
        return null
    }

    override fun <T : Any> get(key: String, type: KClass<T>, converter: Converter<T>): T? = get0(key, converter)

    override fun <T : Any> get(key: String, type: KClass<T>, converter: Converter<T>, def: T): T =
        params[key]?.let {
            try {
                converter.parse(it)
            } catch (ex: IllegalArgumentException) {
                Log.error("Invalid argument \"{0}\" value \"{1}\", using default \"{2}\"", key, "", def)
                def
            }
        } ?: def

    private fun <T : Any> get0(key: String, converter: Converter<T>): T? =
        params[key]?.let {
            try {
                converter.parse(it)
            } catch (ex: IllegalArgumentException) {
                Log.error("Invalid argument \"${key}\" using null")
                null
            }
        }

}
