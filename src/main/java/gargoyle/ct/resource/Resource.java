package gargoyle.ct.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Locale;

/**
 * local and remote (i18nized) resource abstraction for reading and writing
 */
public interface Resource {
    /**
     * checks if this {@link Resource} really exists
     *
     * @return true if exists
     */
    boolean exists();

    /**
     * get localized resource for {@link Locale}
     *
     * @param locale locale
     * @return localized resource
     */
    Resource forLocale(Locale locale);

    /**
     * opens resource for reading
     *
     * @return InputStream to read from
     * @throws IOException on error
     */
    InputStream getInputStream() throws IOException;

    /**
     * current locale
     *
     * @return for localized
     */
    Locale getLocale();

    /**
     * abstract resource path
     *
     * @return location
     */
    String getLocation();

    /**
     * opens resource for writing, if supported
     *
     * @return OutputStream to write to
     * @throws IOException on error
     */
    OutputStream getOutputStream() throws IOException;

    /**
     * if is base resource or derived
     *
     * @return true for non-localized
     */
    boolean isBase();

    /**
     * readable (exists)
     *
     * @return if can read
     */
    boolean isReadable();

    /**
     * writable
     *
     * @return if can write
     */
    boolean isWritable();

    /**
     * absolute resource URL
     *
     * @return URL
     * @throws IOException on error
     */
    URL toURL() throws IOException;

    /**
     * get bound resource with another extension
     *
     * @param extension - extension, such as txt
     * @return new {@link Resource}
     */
    @SuppressWarnings("SameParameterValue")
    Resource withExtension(String extension);
}
