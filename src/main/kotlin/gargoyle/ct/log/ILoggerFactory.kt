package gargoyle.ct.log

import java.io.IOException
import java.io.InputStream

fun interface ILoggerFactory {
    fun getLogger(name: String): Logger

    @Throws(IOException::class)
    fun configure(stream: InputStream) {
    }
}
