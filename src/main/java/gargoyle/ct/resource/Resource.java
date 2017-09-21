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
     * @return
     */
    boolean exists();

    /**
     * get localized resource for {@link Locale}
     *
     * @param locale
     * @return
     */
    Resource forLocale(Locale locale);

    /**
     * opens resource for reading
     *
     * @return InputStream to read from
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;

    /**
     * current locale
     *
     * @return
     */
    Locale getLocale();

    /**
     * abstract resource path
     *
     * @return
     */
    String getLocation();

    /**
     * opens resource for writing, if supported
     *
     * @return OutputStream to write to
     * @throws IOException
     */
    OutputStream getOutputStream() throws IOException;

    /**
     * if is base resource or derived
     *
     * @return
     */
    boolean isBase();

    /**
     * readable (exists)
     *
     * @return
     */
    boolean isReadable();

    /**
     * writable
     *
     * @return
     */
    boolean isWritable();

    /**
     * absolute resource URL
     *
     * @return URL
     * @throws IOException
     */
    URL toURL() throws IOException;

    /**
     * get bound resource with another extension
     *
     * @param extension - extenstion, such as txt
     * @return new {@link Resource}
     */
    @SuppressWarnings("SameParameterValue")
    Resource withExtension(String extension);
}
