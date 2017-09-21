package gargoyle.ct.util.log

import gargoyle.ct.util.util.className
import kotlin.reflect.KClass

object LoggerFactory {
    private val cache: MutableMap<String, Logger> = mutableMapOf()

    private val loggerFactory: Lazy<ILoggerFactory> =
        lazy {
            LoggerFactoryConfig.loggerFactoryClass.constructors.first { it.parameters.isEmpty() }.call()
                .also { it.configure(LoggerFactoryConfig.configuration) }
        }

    fun getLogger(name: String): Logger = cache.getOrPut(name) { loggerFactory.value.getLogger(name) }

    fun getLogger(clazz: KClass<*>): Logger = getLogger(clazz.className)
}
