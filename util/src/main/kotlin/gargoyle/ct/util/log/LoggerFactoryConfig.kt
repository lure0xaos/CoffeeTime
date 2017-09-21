package gargoyle.ct.util.log

import gargoyle.ct.util.log.jul.JULLoggerFactory
import gargoyle.ct.util.util.getResourceAsStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.security.AccessController
import java.security.PrivilegedExceptionAction
import kotlin.reflect.KClass

object LoggerFactoryConfig {
    const val LOGGING_PROPERTIES: String = "config/logging.properties"
    val loggerFactoryClass: KClass<out ILoggerFactory>
        get() = JULLoggerFactory::class

    val configuration: InputStream
        get() = AccessController.doPrivileged(PrivilegedExceptionAction {
            LoggerFactory::class.getResourceAsStream(LOGGING_PROPERTIES)
            { throw FileNotFoundException(LOGGING_PROPERTIES) }
        })
}
