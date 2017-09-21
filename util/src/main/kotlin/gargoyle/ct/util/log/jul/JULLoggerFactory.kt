package gargoyle.ct.util.log.jul

import gargoyle.ct.util.log.ILoggerFactory
import gargoyle.ct.util.log.Logger
import java.io.InputStream
import java.util.logging.LogManager

class JULLoggerFactory : ILoggerFactory {
    override fun getLogger(name: String): Logger = JULLogger(name)

    override fun configure(stream: InputStream): Unit = LogManager.getLogManager().readConfiguration(stream)
}
