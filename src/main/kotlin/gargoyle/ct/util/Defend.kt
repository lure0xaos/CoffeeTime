package gargoyle.ct.util

import java.math.BigDecimal
import java.text.MessageFormat
import kotlin.reflect.KClass

object Defend {
    fun <N> equals(actual: N, expected: N, message: String) {
        if (actual != expected) {
            fail(message)
        }
    }

    private fun fail(message: String, vararg arguments: Any) {
        throw IllegalArgumentException(if (arguments.isEmpty()) message else MessageFormat.format(message, *arguments))
    }

    fun <N : Number?> inRange(value: N, minimum: N, maximum: N, message: String) {
        if (BigDecimal(minimum.toString()) > BigDecimal(value.toString()) ||
            BigDecimal(maximum.toString()) < BigDecimal(value.toString())
        ) {
            fail(message)
        }
    }

    fun <N : Comparable<N>?> inRange(value: N, minimum: N, maximum: N, message: String) {
        if (minimum!! > value || maximum!! < value) {
            fail(message)
        }
    }

    fun isFalse(condition: Boolean, message: String) {
        if (condition) {
            fail(message)
        }
    }

    fun isTrue(condition: Boolean, message: String) {
        if (!condition) {
            fail(message)
        }
    }

    fun notEmpty(value: CharSequence?, message: String) {
        if (value == null || value.isEmpty()) {
            fail(message)
        }
    }

    fun notEmptyTrimmed(value: String?, message: String) {
        if (value == null || value.isBlank()) {
            fail(message)
        }
    }

    fun <T : Any> instanceOf(value: T, type: KClass<out T>, message: String, vararg arguments: Any) {
        if (!type.isInstance(value)) {
            fail(message, *arguments)
        }
    }

    fun <T> notNull(value: T?, message: String) {
        if (value == null) {
            fail(message)
        }
    }

    fun <T> numeric(value: String, message: String, vararg arguments: Any) {
        try {
            notNull(BigDecimal(value), message)
        } catch (e: NumberFormatException) {
            fail(message, *arguments)
        }
    }
}
