package gargoyle.ct.log

import gargoyle.ct.log.jul.JULLoggerFactory
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import kotlin.reflect.KClass

object LoggerFactoryConfig {
    const val MSG_NOT_FOUND = "configuration {0} not found"
    const val LOGGING_PROPERTIES = "/config/logging.properties"
    val loggerFactoryClass: KClass<out ILoggerFactory>
        get() = JULLoggerFactory::class

    @get:Throws(IOException::class)
    val configuration: InputStream
        get() = LoggerFactory::class.java.getResourceAsStream(LOGGING_PROPERTIES)
            ?: throw FileNotFoundException(LOGGING_PROPERTIES)
}
