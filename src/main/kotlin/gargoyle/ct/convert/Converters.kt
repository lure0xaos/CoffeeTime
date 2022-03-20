package gargoyle.ct.convert

import gargoyle.ct.convert.impl.BooleanConverter
import gargoyle.ct.convert.impl.ByteConverter
import gargoyle.ct.convert.impl.BytesConverter
import gargoyle.ct.convert.impl.CharConverter
import gargoyle.ct.convert.impl.DoubleConverter
import gargoyle.ct.convert.impl.FloatConverter
import gargoyle.ct.convert.impl.IntegerConverter
import gargoyle.ct.convert.impl.LongConverter
import gargoyle.ct.convert.impl.ShortConverter
import gargoyle.ct.convert.impl.StringConverter
import kotlin.reflect.KClass

object Converters {
    private val instances: MutableMap<KClass<out Converter<*>>, Converter<*>> = HashMap()
    private val types: MutableMap<KClass<*>, KClass<out Converter<*>>> = HashMap()

    init {
        init()
    }

    operator fun <T : Any> get(type: KClass<T>): Converter<T> {
        require(types.containsKey(type)) { type.toString() }
        val clazz = types[type]!!
        if (instances.containsKey(clazz)) {
            @Suppress("UNCHECKED_CAST")
            return instances[clazz] as Converter<T>
        }
        @Suppress("UNCHECKED_CAST")
        val converter: Converter<T> =
            clazz.constructors.first { it.parameters.isEmpty() }.call() as Converter<T>
        instances[clazz] = converter
        return converter
    }

    private fun init() {
        addConverterClass(Boolean::class, BooleanConverter::class)
        addConverterClass(Byte::class, ByteConverter::class)
        addConverterClass(ByteArray::class, BytesConverter::class)
        addConverterClass(Char::class, CharConverter::class)
        addConverterClass(Double::class, DoubleConverter::class)
        addConverterClass(Float::class, FloatConverter::class)
        addConverterClass(Int::class, IntegerConverter::class)
        addConverterClass(Long::class, LongConverter::class)
        addConverterClass(Short::class, ShortConverter::class)
        addConverterClass(String::class, StringConverter::class)
    }

    private fun <T : Any> addConverterClass(type: KClass<T>, converterClass: KClass<out Converter<T>>) {
        types[type] = converterClass
    }
}
