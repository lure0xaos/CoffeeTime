package gargoyle.ct.util.log

interface Logger {
    fun log(level: Level, exception: Throwable?, pattern: String, vararg arguments: Any)
    fun isLoggable(level: Level): Boolean
    val isTraceEnabled: Boolean
        get() = isLoggable(Level.TRACE)
    val isDebugEnabled: Boolean
        get() = isLoggable(Level.DEBUG)
    val isInfoEnabled: Boolean
        get() = isLoggable(Level.INFO)
    val isWarningEnabled: Boolean
        get() = isLoggable(Level.WARNING)
    val isErrorEnabled: Boolean
        get() = isLoggable(Level.ERROR)

    fun trace(exception: Throwable?, pattern: String, vararg arguments: Any): Unit =
        log(Level.TRACE, exception, pattern, *arguments)

    fun trace(pattern: String, vararg arguments: Any): Unit = log(Level.TRACE, null, pattern, *arguments)

    fun debug(exception: Throwable?, pattern: String, vararg arguments: Any): Unit =
        log(Level.DEBUG, exception, pattern, *arguments)

    fun debug(pattern: String, vararg arguments: Any): Unit = log(Level.DEBUG, null, pattern, *arguments)

    fun error(exception: Throwable?, pattern: String, vararg arguments: Any): Unit =
        log(Level.ERROR, exception, pattern, *arguments)

    fun error(pattern: String, vararg arguments: Any): Unit = log(Level.ERROR, null, pattern, *arguments)

    fun info(exception: Throwable?, pattern: String, vararg arguments: Any): Unit =
        log(Level.INFO, exception, pattern, *arguments)

    fun info(pattern: String, vararg arguments: Any): Unit = log(Level.INFO, null, pattern, *arguments)

    fun log(level: Level, pattern: String, vararg arguments: Any): Unit = log(level, null, pattern, *arguments)

    fun warn(exception: Throwable?, pattern: String, vararg arguments: Any): Unit =
        log(Level.WARNING, exception, pattern, *arguments)

    fun warn(pattern: String, vararg arguments: Any): Unit = log(Level.WARNING, null, pattern, *arguments)

}
