package gargoyle.ct.util.resource.internal

import gargoyle.ct.util.resource.Resource
import gargoyle.ct.util.util.getResource
import java.net.URL
import kotlin.reflect.KClass

/**
 * represents a [Resource] withing application class path (ex. in jar file). writing is mainly unsupported
 */
open class ClassResource : VirtualResource<ClassResource> {
    val loader: KClass<*>

    constructor(loader: KClass<*>, location: String) : super(location) {
        this.loader = loader
    }

    internal constructor(loader: KClass<*>, base: ClassResource, location: String) : super(base, location) {
        this.loader = loader
    }

    override fun createResource(
        base: VirtualResource<ClassResource>?,
        location: String
    ): VirtualResource<ClassResource> =
        if (base == null) ClassResource(loader, location)
        else ClassResource(getLoader(base), base as ClassResource, location)

    override fun toURL(): URL = loader.getResource(location)

    fun getLoader(resource: Resource?): KClass<*> = if (resource is ClassResource) resource.loader else loader

    override fun exists(): Boolean =
        try {
            exists(loader.getResource(location))
        } catch (ex: Exception) {
            false
        }
}
