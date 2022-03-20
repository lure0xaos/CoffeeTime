package gargoyle.ct.messages.util

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.Locale
import java.util.PropertyResourceBundle
import java.util.ResourceBundle

class UTF8Control private constructor(var charset: Charset) : ResourceBundle.Control() {

    @Throws(IOException::class)
    override fun newBundle(
        baseName: String,
        locale: Locale,
        format: String,
        loader: ClassLoader,
        reload: Boolean
    ): ResourceBundle? {
        val bundleName = toBundleName(baseName, locale)
        val resourceName = toResourceName(bundleName, PROPERTIES)
        getStream(loader, reload, resourceName).use { stream ->
            return if (stream != null) PropertyResourceBundle(
                InputStreamReader(stream, charset)
            ) else null
        }
    }

    companion object {
        private const val PROPERTIES = "properties"
        private lateinit var instance: UTF8Control
        val control: UTF8Control
            get() = getControl(StandardCharsets.UTF_8)

        @Synchronized
        fun getControl(charset: Charset): UTF8Control {
            if (!::instance.isInitialized) {
                instance = UTF8Control(charset)
            }
            return instance
        }

        @Throws(IOException::class)
        private fun getStream(loader: ClassLoader, reload: Boolean, resourceName: String): InputStream? {
            if (reload) {
                val url = loader.getResource(resourceName)
                if (url != null) {
                    val connection = url.openConnection()
                    if (connection != null) {
                        connection.useCaches = false
                        return connection.getInputStream()
                    }
                }
            } else {
                return loader.getResourceAsStream(resourceName)
            }
            return null
        }
    }
}
