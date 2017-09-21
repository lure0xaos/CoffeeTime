package gargoyle.ct.util.resource.internal

import gargoyle.ct.util.resource.Resource
import java.io.File
import java.net.URL
import java.util.*

/**
 * represents virtual [Resource] (means physical existence is not required)
 */
open class VirtualResource<R : VirtualResource<R>> : AbstractResource<VirtualResource<R>> {
    private val baseResource: VirtualResource<R>
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
        val control: ResourceBundle.Control =
            ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_PROPERTIES)
        val baseName = baseResource.location.substringBeforeLast('.')
        val suffix = baseResource.location.substringAfterLast('.')
        return control.getCandidateLocales(baseName, locale).map {
            createResource(baseResource, control.toResourceName(control.toBundleName(baseName, it), suffix))
        }.firstOrNull { it.exists() }
            ?.let { it.also { it.locale = locale } }
            ?: baseResource
    }

    override fun createResource(base: VirtualResource<R>?, location: String): VirtualResource<R> =
        base?.let { VirtualResource(it, location) } ?: VirtualResource(location)

    override fun toURL(): URL = if (baseResource == this) super.toURL() else URL(baseResource.toURL(), location)

    val baseName: String
        get() = location.substringBeforeLast(".")

    override fun withExtension(extension: String): Resource? =
        createResource(null, "${location.substringBeforeLast('.')}.$extension")

    override val isBase: Boolean
        get() = baseResource === this
    val extension: String
        get() = location.substringAfterLast(".")
    val name: String
        get() {
            return if (!location.contains('/')) {
                if (!location.contains(File.separatorChar)) location
                else location.substringAfterLast(File.separatorChar)
            } else {
                location.substringAfterLast('/')
            }
        }
    val relativeLocation: String
        get() = (0 until location.length.coerceAtMost(baseResource.location.length))
            .firstOrNull { location[it] != baseResource.location[it] }
            ?.let { location.substring(it) }
            ?: location

}
