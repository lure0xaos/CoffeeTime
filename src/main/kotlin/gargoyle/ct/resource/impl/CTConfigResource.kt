package gargoyle.ct.resource.impl

import gargoyle.ct.config.CTConfig
import gargoyle.ct.ex.CTException
import gargoyle.ct.resource.internal.LocalResource
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.Objects

/**
 * [CTConfig] as [Resource]
 */
class CTConfigResource private constructor(url: URL) : LocalResource(url) {
    companion object {
        private const val SLASH = "/"

        @JvmStatic
        fun findLocalConfig(name: String, existing: Boolean): CTConfigResource? {
            val local: LocalResource = findLocal(name)
            return try {
                if (existing && !local.exists()) null else CTConfigResource(
                    Objects.requireNonNull(
                        local.toURL()
                    )
                )
            } catch (ex: IOException) {
                null
            }
        }

        fun forURL(url: URL): CTConfigResource {
            return CTConfigResource(url)
        }

        fun forURL(root: URL, file: String): CTConfigResource {
            return try {
                val rootString = root.toExternalForm()
                CTConfigResource(URL((if (rootString.endsWith(SLASH)) rootString else rootString + SLASH) + file))
            } catch (ex: MalformedURLException) {
                throw CTException(file, ex)
            }
        }
    }
}
