package gargoyle.ct.resource.internal

import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class TempLocalResource : ClasspathResource {
    private constructor(loader: ClassLoader, base: ClasspathResource, location: String) : super(
        loader,
        base,
        location
    )

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
                val tempFile = File.createTempFile(baseName, DOT + extension)
                Files.copy(stream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
                tempFile.deleteOnExit()
                return TempLocalResource(loader, resource, tempFile.path)
            }
        } catch (e: IOException) {
            throw IllegalArgumentException(location, e)
        }
    }

    @Throws(IOException::class)
    override fun toURL(): URL {
        return File(location).toURI().toURL()
    }

    override fun exists(): Boolean {
        return try {
            exists(toURL())
        } catch (ex: IOException) {
            false
        }
    }

    @Throws(IOException::class)
    fun toFile(): File {
        return File(toURL().path.substring(1))
    }
}
