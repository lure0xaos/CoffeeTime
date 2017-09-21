package gargoyle.ct.util.resource.internal

import gargoyle.ct.util.resource.Resource
import java.net.URL

/**
 * represents a [Resource] withing application class path (ex. in jar file). writing is mainly unsupported
 */
open class ClasspathResource : VirtualResource<ClasspathResource> {
    val loader: ClassLoader

    constructor(location: String) : this(ClasspathResource::class.java.classLoader, location)
    constructor(loader: ClassLoader, location: String) : super(location) {
        this.loader = loader
    }

    internal constructor(loader: ClassLoader, base: ClasspathResource, location: String) : super(base, location) {
        this.loader = loader
    }

    override fun createResource(
        base: VirtualResource<ClasspathResource>?,
        location: String
    ): VirtualResource<ClasspathResource> =
        if (base == null) ClasspathResource(loader, location)
        else ClasspathResource(getLoader(base), base as ClasspathResource, location)

    override fun toURL(): URL = loader.getResource(location)!!

    fun getLoader(resource: Resource?): ClassLoader = if (resource is ClasspathResource) resource.loader else loader

    override fun exists(): Boolean =
        try {
            exists(loader.getResource(location))
        } catch (ex: Exception) {
            false
        }
}
