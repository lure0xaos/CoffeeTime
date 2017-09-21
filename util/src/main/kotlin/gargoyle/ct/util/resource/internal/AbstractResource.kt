package gargoyle.ct.util.resource.internal

import gargoyle.ct.util.resource.Resource
import gargoyle.ct.util.util.simpleClassName
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.URL

/**
 * base functionality for [Resource]
 */
abstract class AbstractResource<R : AbstractResource<R>> protected constructor(override val location: String) :
    AbstractResourceBase(),
    Resource {

    protected abstract fun createResource(base: R?, location: String): R
    override fun exists(): Boolean =
        try {
            exists(toURL())
        } catch (ex: Exception) {
            false
        }

    override val inputStream: InputStream
        get() = getInputStream(toURL())

    override val outputStream: OutputStream
        get() = getOutputStream(toURL(), true)
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

    override fun toURL(): URL = URL(location)

    override fun toString(): String = "${this::class.simpleClassName} [location=${location}]"
}
