package gargoyle.ct.resource.internal

import gargoyle.ct.resource.Resource
import java.io.IOException
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
    ): VirtualResource<ClasspathResource> {
        return if (base == null) ClasspathResource(loader, location) else ClasspathResource(
            getLoader(base),
            base as ClasspathResource,
            location
        )
    }

    @Throws(IOException::class)
    override fun toURL(): URL {
        return loader.getResource(location)!!
    }

    fun getLoader(resource: Resource?): ClassLoader {
        return if (resource is ClasspathResource) resource.loader else loader
    }

    override fun exists(): Boolean {
        return try {
            exists(toURL())
        } catch (ex: IOException) {
            false
        }
    }
}
