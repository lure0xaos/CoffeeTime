package gargoyle.ct;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Locale;

public interface Resource {
    boolean exists();

    Resource forLocale(Locale locale);

    InputStream getInputStream() throws IOException;

    Locale getLocale();

    String getLocation();

    OutputStream getOutputStream() throws IOException;

    URL toURL() throws IOException;

    Resource withExtension(String extension);
}
