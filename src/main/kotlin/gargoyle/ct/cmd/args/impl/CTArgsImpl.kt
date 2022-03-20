package gargoyle.ct.cmd.args.impl

import gargoyle.ct.cmd.args.CTArgs
import gargoyle.ct.convert.Converter
import gargoyle.ct.convert.Converters
import gargoyle.ct.log.Log
import kotlin.reflect.KClass

open class CTArgsImpl(args: Array<String>) : CTArgs {
    private var params: LinkedHashMap<String, String>

    init {
        params = args.associateTo(LinkedHashMap()) { arg ->
            if (arg.contains(EQ)) {
                val pair = arg.split(EQ.toRegex(), 2).toTypedArray()
                pair[0].trim() to pair[1].trim()
            } else {
                val value = arg.trim()
                value to value
            }
        }
    }

    override fun <T : Any> get(type: KClass<T>, key: String): T? {
        return get0(key, Converters[type])
    }

    override fun <T : Any> get(type: KClass<T>, key: String, def: T): T {
        return get(key, type, Converters[type], def)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(index: Int, def: T): T {
        return get(index, def::class as KClass<T>, Converters[def::class as KClass<T>], def)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(key: String, def: T): T {
        return get(key, def::class as KClass<T>, Converters[def::class as KClass<T>], def)
    }

    override fun has(index: Int): Boolean {
        return params.size > index
    }

    override fun has(key: String): Boolean {
        return params.containsKey(key)
    }

    override fun size(): Int {
        return params.size
    }

    override fun <T : Any> get(index: Int, type: KClass<T>, converter: Converter<T>): T? {
        return get0(index, converter)
    }

    override fun <T : Any> get(index: Int, type: KClass<T>, converter: Converter<T>, def: T): T {
        return if (params.size > index) {
            val iterator: Iterator<Map.Entry<String, String>> =
                params.entries.iterator()
            var next = iterator.next()
            var i = 0
            while (iterator.hasNext() && i < index) {
                next = iterator.next()
                i++
            }
            get(next.key, type, converter, def)
        } else def
    }

    private fun <T : Any> get0(index: Int, converter: Converter<T>): T? {
        return if (params.size > index) {
            val iterator: Iterator<Map.Entry<String, String>> =
                params.entries.iterator()
            var next = iterator.next()
            var i = 0
            while (iterator.hasNext() && i < index) {
                next = iterator.next()
                i++
            }
            get0(next.key, converter)
        } else null
    }

    override fun <T : Any> get(key: String, type: KClass<T>, converter: Converter<T>): T? {
        return get0(key, converter)
    }

    override fun <T : Any> get(key: String, type: KClass<T>, converter: Converter<T>, def: T): T {
        return if (params.containsKey(key)) {
            val value = params[key]
            try {
                converter.parse(value!!)
            } catch (ex: IllegalArgumentException) {
                Log.error(MSG_INVALID_ARGUMENT, key, value.toString(), def)
                def
            }
        } else {
            def
        }
    }

    private fun <T : Any> get0(key: String, converter: Converter<T>): T? {
        return if (params.containsKey(key)) {
            val value = params[key]
            try {
                converter.parse(value!!)
            } catch (ex: IllegalArgumentException) {
                Log.error(MSG_INVALID_ARGUMENT, key, value.toString())
                null
            }
        } else {
            null
        }
    }

    companion object {
        private const val EQ = "="
        private const val MSG_INVALID_ARGUMENT = "Invalid argument \"{0}\" value \"{1}\", using default \"{2}\""
    }
}
