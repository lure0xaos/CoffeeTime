package gargoyle.ct.log.jul

import gargoyle.ct.log.ILoggerFactory
import gargoyle.ct.log.Logger
import java.io.IOException
import java.io.InputStream
import java.util.logging.LogManager

class JULLoggerFactory : ILoggerFactory {
    override fun getLogger(name: String): Logger {
        return JULLogger(name)
    }

    @Throws(IOException::class)
    override fun configure(stream: InputStream) {
        LogManager.getLogManager().readConfiguration(stream)
    }
}
