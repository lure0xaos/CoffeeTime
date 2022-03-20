package gargoyle.ct.log

import gargoyle.ct.messages.util.UTF8Control
import java.nio.charset.StandardCharsets
import java.util.Locale
import java.util.ResourceBundle

object Log {
    private const val LOCATION_ERRORS = "messages/errors"
    fun debug(exception: Throwable?, pattern: String, vararg arguments: Any) {
        doLog(Level.DEBUG, exception, pattern, *arguments)
    }

    private fun doLog(level: Level, exception: Throwable?, pattern: String, vararg arguments: Any) {
        val ste = findCaller()
        if (ste != null) {
            val logger = LoggerFactory.getLogger(ste.className)
            if (logger.isLoggable(level)) {
                logger.log(level, exception, pattern, *arguments)
            }
        }
    }

    private val resourceBundle: ResourceBundle
        get() = ResourceBundle.getBundle(
            LOCATION_ERRORS,
            Locale.getDefault(),
            UTF8Control.getControl(StandardCharsets.UTF_8)
        )

    private fun findCaller(): StackTraceElement? {
        val trace = Thread.currentThread().stackTrace
        val length = trace.size
        for (i in 1 until length) {
            val ste = trace[i]
            if (Log::class.qualifiedName != ste.className) {
                return ste
            }
        }
        return null
    }

    fun trace(pattern: String, vararg arguments: Any) {
        doLog(Level.TRACE, null, pattern, *arguments)
    }

    fun debug(pattern: String, vararg arguments: Any) {
        doLog(Level.DEBUG, null, pattern, *arguments)
    }

    fun error(exception: Throwable?, pattern: String, vararg arguments: Any) {
        doLog(Level.ERROR, exception, pattern, *arguments)
    }

    fun error(pattern: String, vararg arguments: Any) {
        doLog(Level.ERROR, null, pattern, *arguments)
    }

    fun info(exception: Throwable?, pattern: String, vararg arguments: Any) {
        doLog(Level.INFO, exception, pattern, *arguments)
    }

    @JvmStatic
    fun info(pattern: String, vararg arguments: Any) {
        doLog(Level.INFO, null, pattern, *arguments)
    }

    fun log(level: Level, exception: Throwable?, pattern: String, vararg arguments: Any) {
        doLog(level, exception, pattern, *arguments)
    }

    fun log(level: Level, pattern: String, vararg arguments: Any) {
        doLog(level, null, pattern, *arguments)
    }

    fun warn(exception: Throwable?, pattern: String, vararg arguments: Any) {
        doLog(Level.WARNING, exception, pattern, *arguments)
    }

    fun warn(pattern: String, vararg arguments: Any) {
        doLog(Level.WARNING, null, pattern, *arguments)
    }
}
