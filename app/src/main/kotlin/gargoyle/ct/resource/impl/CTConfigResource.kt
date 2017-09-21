package gargoyle.ct.resource.impl

import gargoyle.ct.config.CTConfig
import gargoyle.ct.ex.CTException
import gargoyle.ct.util.resource.internal.LocalResource
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

/**
 * [CTConfig] as [Resource]
 */
class CTConfigResource private constructor(url: URL) : LocalResource(url) {
    companion object {

        fun findLocalConfig(name: String, existing: Boolean): CTConfigResource? =
            findLocal(name).let {
                try {
                    if (existing && !it.exists()) null else CTConfigResource(requireNotNull(it.toURL()))
                } catch (ex: IOException) {
                    null
                }
            }

        fun forURL(url: URL): CTConfigResource = CTConfigResource(url)

        fun forURL(root: URL, file: String): CTConfigResource =
            try {
                CTConfigResource(URL("${root.toExternalForm().trimEnd('/')}/$file"))
            } catch (ex: MalformedURLException) {
                throw CTException(file, ex)
            }
    }
}
