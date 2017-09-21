package gargoyle.ct.util.resource.internal

import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class TempLocalResource : ClasspathResource {
    private constructor(loader: ClassLoader, base: ClasspathResource, location: String) :
            super(loader, base, location)

    constructor(location: String) : super(location)

    override fun createResource(
        base: VirtualResource<ClasspathResource>?,
        location: String
    ): VirtualResource<ClasspathResource> {
        val loader = getLoader(base)
        val resource: ClasspathResource =
            base?.let { ClasspathResource(loader, it as ClasspathResource, location) } ?: ClasspathResource(location)
        if (!resource.exists()) {
            return TempLocalResource(location)
        }
        try {
            resource.inputStream.use { stream ->
                val tempFile = File.createTempFile(baseName, ".$extension")
                Files.copy(stream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
                tempFile.deleteOnExit()
                return TempLocalResource(loader, resource, tempFile.path)
            }
        } catch (e: IOException) {
            throw IllegalArgumentException(location, e)
        }
    }

    override fun toURL(): URL = File(location).toURI().toURL()

    override fun exists(): Boolean =
        try {
            exists(toURL())
        } catch (ex: Exception) {
            false
        }

    fun toFile(): File = File(toURL().path.substring(1))
}
