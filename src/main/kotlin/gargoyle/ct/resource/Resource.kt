package gargoyle.ct.resource

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.util.Locale

/**
 * local and remote (i18d) resource abstraction for reading and writing
 */
interface Resource {
    /**
     * checks if this [Resource] really exists
     *
     * @return true if exists
     */
    fun exists(): Boolean

    /**
     * get localized resource for [Locale]
     *
     * @param locale locale
     * @return localized resource
     */
    fun forLocale(locale: Locale): Resource?

    /**
     * opens resource for reading
     *
     * @return InputStream to read from
     * @throws IOException on error
     */
    @get:Throws(IOException::class)
    val inputStream: InputStream

    /**
     * current locale
     *
     * @return for localized
     */
    val locale: Locale

    /**
     * abstract resource path
     *
     * @return location
     */
    val location: String

    /**
     * opens resource for writing, if supported
     *
     * @return OutputStream to write to
     * @throws IOException on error
     */
    @get:Throws(IOException::class)
    val outputStream: OutputStream

    /**
     * if is base resource or derived
     *
     * @return true for non-localized
     */
    val isBase: Boolean

    /**
     * readable (exists)
     *
     * @return if one can read
     */
    val isReadable: Boolean

    /**
     * writable
     *
     * @return if one can write
     */
    val isWritable: Boolean

    /**
     * absolute resource URL
     *
     * @return URL
     * @throws IOException on error
     */
    @Throws(IOException::class)
    fun toURL(): URL

    /**
     * get bound resource with another extension
     *
     * @param extension - extension, such as txt
     * @return new [Resource]
     */
    fun withExtension(extension: String): Resource?
}
