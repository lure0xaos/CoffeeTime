package gargoyle.ct.util.convert

import java.util.*
import kotlin.reflect.KClass

object Converters {
    private val converters: MutableMap<KClass<*>, Converter<*>> =
        mutableMapOf(Boolean::class to object : Converter<Boolean> {
            override fun format(data: Boolean): String = data.toString()
            override fun parse(data: String): Boolean = data.toBoolean()
        }, Byte::class to object : Converter<Byte> {
            override fun format(data: Byte): String = data.toString()
            override fun parse(data: String): Byte = data.toByte()
        }, ByteArray::class to object : Converter<ByteArray> {
            override fun format(data: ByteArray): String = Base64.getEncoder().encodeToString(data)
            override fun parse(data: String): ByteArray = Base64.getDecoder().decode(data)
        }, Char::class to object : Converter<Char> {
            override fun format(data: Char): String = data.toString()
            override fun parse(data: String): Char = data[0]
        }, Double::class to object : Converter<Double> {
            override fun format(data: Double): String = data.toString()
            override fun parse(data: String): Double = data.toDouble()
        }, Float::class to object : Converter<Float> {
            override fun format(data: Float): String = data.toString()
            override fun parse(data: String): Float = data.toFloat()
        }, Int::class to object : Converter<Int> {
            override fun format(data: Int): String = data.toString()
            override fun parse(data: String): Int = data.toInt()
        }, Long::class to object : Converter<Long> {
            override fun format(data: Long): String = data.toString()
            override fun parse(data: String): Long = data.toLong()
        }, Short::class to object : Converter<Short> {
            override fun format(data: Short): String = data.toString()
            override fun parse(data: String): Short = data.toShort()
        }, String::class to object : Converter<String> {
            override fun format(data: String): String = data
            override fun parse(data: String): String = data
        })

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(type: KClass<T>): Converter<T> = converters[type] as Converter<T>

}
