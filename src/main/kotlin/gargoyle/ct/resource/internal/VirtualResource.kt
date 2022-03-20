package gargoyle.ct.resource.internal

import gargoyle.ct.messages.util.UTF8Control
import gargoyle.ct.resource.Resource
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.Locale
import java.util.ResourceBundle

/**
 * represents virtual [Resource] (means physical existence is not required)
 */
open class VirtualResource<R : VirtualResource<R>> : AbstractResource<VirtualResource<R>> {
    private val baseResource: VirtualResource<R>
    private val control: ResourceBundle.Control = UTF8Control.getControl(StandardCharsets.UTF_8)
    final override lateinit var locale: Locale
        private set

    protected constructor(baseResource: VirtualResource<R>, location: String) : super(location) {
        this.baseResource = baseResource
    }

    protected constructor(location: String) : super(location) {
        @Suppress("LeakingThis")
        baseResource = this
    }

    override fun forLocale(locale: Locale): Resource {
        val location = baseResource.location
        val l = location.lastIndexOf(CHAR_DOT)
        val baseName = location.substring(0, l)
        val suffix = location.substring(l + 1)
        for (specificLocale in control.getCandidateLocales(baseName, locale)) {
            val loc = control.toResourceName(control.toBundleName(baseName, specificLocale), suffix)
            var resource: VirtualResource<R>? = null
            try {
                resource = createResource(baseResource, loc)
            } catch (ignored: RuntimeException) {
            }
            if (resource != null && resource.exists()) {
                resource.locale = locale
                return resource
            }
        }
        return baseResource
    }

    override fun createResource(base: VirtualResource<R>?, location: String): VirtualResource<R> {
        return (base?.let { VirtualResource(it, location) } ?: VirtualResource(location))
    }

    @Throws(IOException::class)
    override fun toURL(): URL {
        return if (baseResource == this) super.toURL() else URL(baseResource.toURL(), location)
    }

    val baseName: String
        get() {
            val location = location
            val l = location.lastIndexOf(DOT)
            return if (l < 0) location else location.substring(0, l)
        }

    override fun withExtension(extension: String): Resource? {
        val location = location
        val loc = StringBuilder().append(location.substring(0, location.lastIndexOf(CHAR_DOT)))
            .append(DOT).append(extension).toString()
        return createResource(null, loc)
    }

    override val isBase: Boolean
        get() = baseResource === this
    val extension: String
        get() {
            val location = location
            return location.substring(location.lastIndexOf(DOT) + 1)
        }
    val name: String
        get() {
            val location = location
            val l1 = location.lastIndexOf('/')
            return if (l1 < 0) {
                val l2 = location.lastIndexOf(File.separatorChar)
                if (l2 < 0) location else location.substring(l2)
            } else {
                location.substring(l1)
            }
        }
    val relativeLocation: String
        get() {
            val thisLocation = location
            val baseLocation = baseResource.location
            val minLength = thisLocation.length.coerceAtMost(baseLocation.length)
            for (i in 0 until minLength) {
                if (thisLocation[i] != baseLocation[i]) {
                    return thisLocation.substring(i)
                }
            }
            return thisLocation
        }

    companion object {
        const val DOT = "."
        private const val CHAR_DOT = '.'
    }
}
