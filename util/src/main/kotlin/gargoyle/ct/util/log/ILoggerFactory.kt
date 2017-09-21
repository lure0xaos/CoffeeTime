package gargoyle.ct.util.log

import java.io.InputStream

fun interface ILoggerFactory {
    fun getLogger(name: String): Logger

    fun configure(stream: InputStream): Unit = Unit
}
