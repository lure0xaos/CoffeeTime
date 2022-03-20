package gargoyle.ct.resource.internal

import gargoyle.ct.resource.Resource
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.text.MessageFormat

/**
 * base functionality for [Resource]
 */
abstract class AbstractResource<R : AbstractResource<R>> protected constructor(override val location: String) :
    AbstractResourceBase(),
    Resource {

    protected abstract fun createResource(base: R?, location: String): R
    override fun exists(): Boolean {
        return try {
            exists(toURL())
        } catch (ex: IOException) {
            false
        }
    }

    @get:Throws(IOException::class)
    override val inputStream: InputStream
        get() {
            val url = toURL()
            return getInputStream(url)
        }

    @get:Throws(IOException::class)
    override val outputStream: OutputStream
        get() {
            val url = toURL()
            return getOutputStream(url, true)
        }
    override val isBase: Boolean
        get() = true
    override val isReadable: Boolean
        get() = try {
            isReadable(toURL())
        } catch (e: IOException) {
            false
        }
    override val isWritable: Boolean
        get() = try {
            isWritable(toURL())
        } catch (e: IOException) {
            false
        }

    @Throws(IOException::class)
    override fun toURL(): URL {
        return URL(location)
    }

    override fun toString(): String {
        return MessageFormat.format("{1} [location={0}]", location, javaClass.simpleName)
    }
}
