package gargoyle.ct.log

import kotlin.reflect.KClass

object LoggerFactory {
    private val cache: MutableMap<String, Logger> = mutableMapOf()

    @get:Synchronized
    private var loggerFactory: Lazy<ILoggerFactory> = lazy {
        LoggerFactoryConfig.loggerFactoryClass.constructors.first { it.parameters.isEmpty() }.call()
            .also { it.configure(LoggerFactoryConfig.configuration) }
    }


    fun getLogger(name: String): Logger {
        return cache.getOrPut(name) { loggerFactory.value.getLogger(name) }
    }

    fun getLogger(clazz: KClass<*>): Logger {
        return getLogger(clazz.qualifiedName!!)
    }
}
