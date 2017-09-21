package gargoyle.ct.util.log

import java.util.*

object Log {

    fun error(exception: Throwable?, pattern: String, vararg arguments: Any): Unit =
        doLog(Level.ERROR, exception, pattern, *arguments)

    fun error(pattern: String, vararg arguments: Any): Unit = doLog(Level.ERROR, null, pattern, *arguments)

    fun warn(exception: Throwable?, pattern: String, vararg arguments: Any): Unit =
        doLog(Level.WARNING, exception, pattern, *arguments)

    fun warn(pattern: String, vararg arguments: Any): Unit = doLog(Level.WARNING, null, pattern, *arguments)

    fun info(exception: Throwable?, pattern: String, vararg arguments: Any): Unit =
        doLog(Level.INFO, exception, pattern, *arguments)

    fun info(pattern: String, vararg arguments: Any): Unit = doLog(Level.INFO, null, pattern, *arguments)

    fun debug(exception: Throwable?, pattern: String, vararg arguments: Any) {
        doLog(Level.DEBUG, exception, pattern, *arguments)
    }

    fun trace(pattern: String, vararg arguments: Any): Unit = doLog(Level.TRACE, null, pattern, *arguments)

    fun debug(pattern: String, vararg arguments: Any): Unit = doLog(Level.DEBUG, null, pattern, *arguments)

    fun log(level: Level, exception: Throwable?, pattern: String, vararg arguments: Any): Unit =
        doLog(level, exception, pattern, *arguments)

    private fun doLog(level: Level, exception: Throwable?, pattern: String, vararg arguments: Any) {
        findCaller()
            ?.let { LoggerFactory.getLogger(it.className) }
            ?.let { if (it.isLoggable(level)) it.log(level, exception, pattern, *arguments) }
    }

    fun log(level: Level, pattern: String, vararg arguments: Any): Unit = doLog(level, null, pattern, *arguments)

    private const val LOCATION_ERRORS = "errors"

    private val resourceBundle: ResourceBundle by lazy {
        ResourceBundle.getBundle(LOCATION_ERRORS, Locale.getDefault())
    }

    private fun findCaller(): StackTraceElement? =
        Thread.currentThread().stackTrace.firstOrNull { element: StackTraceElement ->
            PACKAGES.none {
                element.className.startsWith(
                    it
                )
            }
        }

    private val PACKAGES = arrayOf(
        "java.",
        "javax.",
        "kotlin.",
        "kotlinx.",
        Log::class.java.packageName
    )
}
