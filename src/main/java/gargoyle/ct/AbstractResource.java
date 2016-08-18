package gargoyle.ct;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

abstract class AbstractResource implements Resource {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)";
    private static final String PROP_UA = "User-Agent";
    private static final String METHOD_HEAD = "HEAD";
    private final String location;

    protected AbstractResource(final String location) {
        this.location = location;
    }

    protected abstract <R extends Resource> R createResource(final R base, final String location);

    @Override
    public boolean exists() {
        try {
            return this.exists(this.toURL());
        } catch (final IOException ex) {
            return false;
        }
    }

    protected boolean exists(final URL url) {
        if (url != null) {
            try {
                final URLConnection connection = url.openConnection();
                if (connection instanceof HttpURLConnection) {
                    final HttpURLConnection huc = (HttpURLConnection) connection;
                    huc.setInstanceFollowRedirects(false);
                    huc.setRequestMethod(AbstractResource.METHOD_HEAD);
                    huc.setRequestProperty(AbstractResource.PROP_UA, AbstractResource.USER_AGENT);
                    huc.connect();
                    return huc.getResponseCode() == HttpURLConnection.HTTP_OK;
                }
                if ("file".equalsIgnoreCase(url.getProtocol())) {
                    return new File(url.toURI()).exists();
                }
            } catch (final IOException ex) {
                return false;
            } catch (final URISyntaxException ex) {
                return false;
            }
            return false;
        }
        return false;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.toURL().openConnection().getInputStream();
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return this.toURL().openConnection().getOutputStream();
    }

    @Override
    public String toString() {
        return "AbstractResource [location=" + this.location + "]";
    }

    @Override
    public URL toURL() throws IOException {
        return new URL(this.location);
    }
}
