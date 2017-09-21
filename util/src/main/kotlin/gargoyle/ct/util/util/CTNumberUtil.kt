package gargoyle.ct.util.util

import kotlin.reflect.KClass
import kotlin.reflect.full.functions

object CTNumberUtil {
    private const val METHOD_VALUE_OF = "valueOf"
    private const val MSG_CANNOT_CREATE_INSTANCE_OF_0 = "Cannot create instance of {0}"

    @Suppress("UNCHECKED_CAST")
    fun <T : Number> fromInt(type: KClass<T>, value: Int): T {
        return when (type) {
            Int::class -> {
                return value as T
            }

            Long::class -> {
                return value.toLong() as T
            }

            Double::class -> {
                return value.toDouble() as T
            }

            Float::class -> {
                return value.toFloat() as T
            }

            Byte::class -> {
                value.toByte() as T
            }

            else -> type.functions.first { it.name == METHOD_VALUE_OF }.call(value.toString()) as T
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getDefault(type: KClass<T>): T {
        when (type) {
            Int::class -> {
                return (0) as T
            }

            Long::class -> {
                return (0) as T
            }

            Double::class -> {
                return (0.0) as T
            }

            Float::class -> {
                return (0f) as T
            }

            Byte::class -> {
                return (0.toByte()) as T
            }

            else -> {
                return if ((type is Number)) type.functions.first { it.name == METHOD_VALUE_OF }.call("0") as T
                else type.constructors.first { it.parameters.isEmpty() }.call()
            }
        }
    }

    fun getInteger(value: Any): Int = (value.toString()).toInt()

    fun toRange(value: Int, min: Int, max: Int): Int = min.coerceAtLeast(max.coerceAtMost(value))

    fun <T : Number> toRange(min: T, max: T, value: T): Int =
        toInt(min).coerceAtLeast(toInt(max).coerceAtMost(toInt(value)))

    fun <T : Number> toInt(value: T): Int = value.toString().toInt()
}
