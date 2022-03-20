package gargoyle.ct.log.jul

import gargoyle.ct.log.Level
import gargoyle.ct.log.Logger
import java.text.MessageFormat
import java.util.logging.LogRecord

class JULLogger(name: String?) : Logger {
    private val logger: java.util.logging.Logger

    init {
        logger = java.util.logging.Logger.getLogger(name)
    }

    override fun log(level: Level, exception: Throwable?, pattern: String, vararg arguments: Any) {
        val record = LogRecord(
            getLevel(level),
            MessageFormat.format(pattern, *arguments)
        )
        record.thrown = exception
        logger.log(record)
    }

    private fun getLevel(level: Level): java.util.logging.Level {
        return when (level) {
            Level.TRACE -> java.util.logging.Level.FINER
            Level.DEBUG -> java.util.logging.Level.FINE
            Level.INFO -> java.util.logging.Level.INFO
            Level.WARNING -> java.util.logging.Level.WARNING
            Level.ERROR -> java.util.logging.Level.SEVERE
        }
    }

    override fun isLoggable(level: Level): Boolean {
        return logger.isLoggable(getLevel(level))
    }
}
