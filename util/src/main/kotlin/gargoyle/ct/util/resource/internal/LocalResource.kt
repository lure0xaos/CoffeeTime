package gargoyle.ct.util.resource.internal

import gargoyle.ct.util.util.ResourceException
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import java.text.MessageFormat

/**
 * local filesystem [Resource] from different locations
 */
open class LocalResource protected constructor(url: URL) : VirtualResource<LocalResource>(url.toExternalForm()) {
    fun toFile(): File {
        val url = toURL()
        val path = url.path
        return File(if (path.startsWith(File.separator)) path.substring(1) else path) // XXX hacky
    }

    private class LocalLocation(private val url: URL, val readPriority: Int, val writePriority: Int) {

        fun getUrl(): URL {
            return url
        }

        override fun hashCode(): Int {
            return url.toExternalForm().hashCode()
        }

        override fun equals(other: Any?): Boolean =
            when {
                this === other -> true
                other == null || javaClass != other.javaClass -> false
                else -> {
                    val that = other as LocalLocation
                    url.toExternalForm() == that.url.toExternalForm()
                }
            }
    }

    private class LocalLocationReadPriority : Comparator<LocalLocation> {
        override fun compare(o1: LocalLocation, o2: LocalLocation): Int {
            return o1.readPriority.compareTo(o2.readPriority)
        }
    }

    private class LocalLocationWritePriority : Comparator<LocalLocation> {
        override fun compare(o1: LocalLocation, o2: LocalLocation): Int = o1.writePriority.compareTo(o2.writePriority)
    }

    companion object {
        private const val ENV_USER_DIR = "user.dir"
        private const val ENV_USER_HOME = "user.home"
        private const val MSG_CANNOT_CREATE_ROOTS = "Cannot create roots"
        private const val MSG_CANNOT_USE_0_AS_ROOT = "Cannot use {0} as root"
        fun findLocal(name: String): LocalResource {
            var writable: LocalResource? = null
            var writablePriority = Int.MIN_VALUE
            for (root in readableLocations) {
                try {
                    val resource = LocalResource(URL(root.getUrl(), name))
                    if (resource.exists()) {
                        return resource
                    }
                    val writePriority: Int = root.writePriority
                    if (resource.isWritable && (writable == null || writablePriority < writePriority)) {
                        writable = resource
                        writablePriority = writePriority
                    }
                } catch (ex: MalformedURLException) {
                    throw ResourceException(MessageFormat.format(MSG_CANNOT_USE_0_AS_ROOT, root), ex)
                }
            }
            return writable!!
        }

        private val readableLocations: Array<LocalLocation>
            get() = locations
        private val locations: Array<LocalLocation>
            get() = try {
                arrayOf(
                    LocalLocation(File(".").toURI().toURL(), 0, 0),
                    LocalLocation(File(System.getProperty(ENV_USER_DIR, ".")).toURI().toURL(), 0, 0),
                    LocalLocation(homeDirectoryLocation, 0, 1),
                    //FIXME
//                    LocalLocation(
//                        URL(
//                            LocalLocation::class.getResource("/").toExternalForm().substringBeforeLast('/')
//                        ), 0, 0
//                    )
                )
            } catch (ex: MalformedURLException) {
                throw ResourceException(MSG_CANNOT_CREATE_ROOTS, ex)
            }
        private val writableLocations: Array<LocalLocation>
            get() = locations
        val homeDirectoryLocation: URL
            get() = try {
                File(System.getProperty(ENV_USER_HOME, ".")).toURI().toURL()
            } catch (e: MalformedURLException) {
                throw ResourceException(e.localizedMessage, e)
            }
    }
}
