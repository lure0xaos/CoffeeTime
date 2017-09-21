package gargoyle.ct.util.resource.internal

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.*
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
            if (SCHEME_HTTP.equals(url.protocol, ignoreCase = true)) {
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
            if (SCHEME_FILE.equals(url.protocol, ignoreCase = true)) {
                return Files.exists(Paths.get(url.toURI()))
            }
            if (SCHEME_JAR.equals(url.protocol, ignoreCase = true)) {
                val connection = url.openConnection()
                if (connection is JarURLConnection) {
                    connection.setRequestProperty(KEY_USER_AGENT, VALUE_USER_AGENT)
                    connection.connect()
                    connection.jarFile.use { jarFile -> return jarFile != null }
                }
            }
            if (SCHEME_JRT.equals(url.protocol, ignoreCase = true)) {
                val connection = url.openConnection()
                if (connection is URLConnection) {
                    connection.setRequestProperty(KEY_USER_AGENT, VALUE_USER_AGENT)
                    connection.connect()
                    return connection.contentLengthLong != 0L
                }
            }
            error("unsupported protocol: ${url.protocol} ($url)")
        } catch (ex: IOException) {
            false
        } catch (ex: URISyntaxException) {
            false
        }
    }

    fun getInputStream(url: URL): InputStream {
        when {
            url.protocol.equals(SCHEME_HTTP, ignoreCase = true) -> {
                val connection = url.openConnection()
                if (connection is HttpURLConnection) {
                    connection.setRequestProperty(KEY_USER_AGENT, VALUE_USER_AGENT)
                    return connection.getInputStream()
                }
            }

            url.protocol.equals(SCHEME_FILE, ignoreCase = true) -> {
                return try {
                    Files.newInputStream(Paths.get(url.toURI()))
                } catch (e: URISyntaxException) {
                    throw IOException(e.localizedMessage, e)
                }
            }

            url.protocol.equals(SCHEME_JAR, ignoreCase = true) -> {
                val connection = url.openConnection()
                if (connection is JarURLConnection) {
                    connection.setRequestProperty(KEY_USER_AGENT, VALUE_USER_AGENT)
                    return connection.getInputStream()
                }
            }

            url.protocol.equals(SCHEME_JRT, ignoreCase = true) -> {
                val connection = url.openConnection()
                if (connection is URLConnection) {
                    connection.setRequestProperty(KEY_USER_AGENT, VALUE_USER_AGENT)
                    return connection.getInputStream()
                }
            }
        }
        error("unsupported protocol: ${url.protocol}")
    }

    protected fun isWritable(url: URL): Boolean {
        try {
            getOutputStream(url, false).use { return true }
        } catch (e: IOException) {
            return false
        }
    }

    protected fun getOutputStream(url: URL, create: Boolean): OutputStream {
        when {
            url.protocol.equals(SCHEME_HTTP, ignoreCase = true) -> {
                val connection = url.openConnection()
                if (connection is HttpURLConnection) {
                    connection.setRequestProperty(KEY_USER_AGENT, VALUE_USER_AGENT)
                    return connection.getOutputStream()
                }
            }

            url.protocol.equals(SCHEME_FILE, ignoreCase = true) -> {
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

            url.protocol.equals(SCHEME_JAR, ignoreCase = true) -> {
                val connection = url.openConnection()
                if (connection is JarURLConnection) {
                    connection.setRequestProperty(KEY_USER_AGENT, VALUE_USER_AGENT)
                    return connection.getOutputStream()
                }
            }

            url.protocol.equals(SCHEME_JRT, ignoreCase = true) -> {
                val connection = url.openConnection()
                if (connection is URLConnection) {
                    connection.setRequestProperty(KEY_USER_AGENT, VALUE_USER_AGENT)
                    return connection.getOutputStream()
                }
            }
        }
        error("unsupported protocol: ${url.protocol}")
    }

    companion object {
        private const val KEY_USER_AGENT = "User-Agent"
        private const val METHOD_HEAD = "HEAD"
        private const val SCHEME_FILE = "file"
        private const val SCHEME_HTTP = "http"
        private const val SCHEME_JAR = "jar"
        private const val SCHEME_JRT = "jrt"
        private const val VALUE_USER_AGENT =
            "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)"
    }
}
