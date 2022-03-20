package gargoyle.ct.resource.internal

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.JarURLConnection
import java.net.URISyntaxException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

abstract class AbstractResourceBase {
    protected fun isReadable(url: URL): Boolean {
        if (!exists(url)) {
            return false
        }
        try {
            getInputStream(url).use { return true }
        } catch (e: IOException) {
            return false
        }
    }

    protected fun exists(url: URL?): Boolean {
        return if (url == null) {
            false
        } else try {
            val protocol = url.protocol
            if (SCHEME_HTTP.equals(protocol, ignoreCase = true)) {
                val connection = url.openConnection()
                if (connection is HttpURLConnection) {
                    connection.instanceFollowRedirects = false
                    connection.requestMethod = METHOD_HEAD
                    connection.setRequestProperty(KEY_USER_AGENT, VALUE_USER_AGENT)
                    connection.connect()
                    val responseCode = connection.responseCode
                    connection.disconnect()
                    return responseCode == HttpURLConnection.HTTP_OK
                }
            }
            if (SCHEME_FILE.equals(protocol, ignoreCase = true)) {
                return Files.exists(Paths.get(url.toURI()))
            }
            if (SCHEME_JAR.equals(protocol, ignoreCase = true)) {
                val connection = url.openConnection()
                if (connection is JarURLConnection) {
                    connection.setRequestProperty(KEY_USER_AGENT, VALUE_USER_AGENT)
                    connection.connect()
                    connection.jarFile.use { jarFile -> return jarFile != null }
                }
            }
            fail(protocol)
        } catch (ex: IOException) {
            false
        } catch (ex: URISyntaxException) {
            false
        }
    }

    private fun <T> fail(protocol: String): T {
        throw UnsupportedOperationException("unsupported protocol: $protocol")
    }

    @Throws(IOException::class)
    fun getInputStream(url: URL): InputStream {
        val protocol = url.protocol
        if (SCHEME_HTTP.equals(protocol, ignoreCase = true)) {
            val connection = url.openConnection()
            if (connection is HttpURLConnection) {
                connection.setRequestProperty(KEY_USER_AGENT, VALUE_USER_AGENT)
                return connection.getInputStream()
            }
        }
        if (SCHEME_FILE.equals(protocol, ignoreCase = true)) {
            return try {
                Files.newInputStream(Paths.get(url.toURI()))
            } catch (e: URISyntaxException) {
                throw IOException(e.localizedMessage, e)
            }
        }
        if (SCHEME_JAR.equals(protocol, ignoreCase = true)) {
            val connection = url.openConnection()
            if (connection is JarURLConnection) {
                connection.setRequestProperty(KEY_USER_AGENT, VALUE_USER_AGENT)
                return connection.getInputStream()
            }
        }
        return fail(protocol)
    }

    protected fun isWritable(url: URL): Boolean {
        try {
            getOutputStream(url, false).use { return true }
        } catch (e: IOException) {
            return false
        }
    }

    @Throws(IOException::class)
    protected fun getOutputStream(url: URL, create: Boolean): OutputStream {
        val protocol = url.protocol
        if (SCHEME_HTTP.equals(protocol, ignoreCase = true)) {
            val connection = url.openConnection()
            if (connection is HttpURLConnection) {
                connection.setRequestProperty(KEY_USER_AGENT, VALUE_USER_AGENT)
                return connection.getOutputStream()
            }
        }
        if (SCHEME_FILE.equals(protocol, ignoreCase = true)) {
            return try {
                val path = Paths.get(url.toURI())
                val exists = Files.exists(path)
                try {
                    Files.newOutputStream(path, StandardOpenOption.CREATE)
                } finally {
                    if (!create && !exists) {
                        Files.delete(path)
                    }
                }
            } catch (e: URISyntaxException) {
                throw IOException(e.localizedMessage, e)
            }
        }
        if (SCHEME_JAR.equals(protocol, ignoreCase = true)) {
            val connection = url.openConnection()
            if (connection is JarURLConnection) {
                connection.setRequestProperty(KEY_USER_AGENT, VALUE_USER_AGENT)
                return connection.getOutputStream()
            }
        }
        return fail(protocol)
    }

    companion object {
        private const val KEY_USER_AGENT = "User-Agent"
        private const val METHOD_HEAD = "HEAD"
        private const val SCHEME_FILE = "file"
        private const val SCHEME_HTTP = "http"
        private const val SCHEME_JAR = "jar"
        private const val VALUE_USER_AGENT =
            "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)"
    }
}
