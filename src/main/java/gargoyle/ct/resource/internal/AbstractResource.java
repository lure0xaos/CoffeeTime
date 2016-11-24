package gargoyle.ct.resource.internal;

import gargoyle.ct.resource.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;

abstract class AbstractResource implements Resource {

    private static final String
        USER_AGENT =
        "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)";

    private static final String PROP_UA = "User-Agent";

    private static final String METHOD_HEAD = "HEAD";

    private static final String SCHEME_FILE = "file";

    private final String location;

    protected AbstractResource(String location) {
        this.location = location;
    }

    protected abstract <R extends Resource> R createResource(R base, String location);

    @Override
    public boolean exists() {
        try {
            return exists(toURL());
        } catch (IOException ex) {
            return false;
        }
    }

    protected boolean exists(URL url) {
        if (url != null) {
            try {
                URLConnection connection = url.openConnection();
                if (connection instanceof HttpURLConnection) {
                    HttpURLConnection huc = (HttpURLConnection) connection;
                    huc.setInstanceFollowRedirects(false);
                    huc.setRequestMethod(METHOD_HEAD);
                    huc.setRequestProperty(PROP_UA, USER_AGENT);
                    huc.connect();
                    return huc.getResponseCode() == HttpURLConnection.HTTP_OK;
                }
                //noinspection CallToStringEqualsIgnoreCase
                if (SCHEME_FILE.equalsIgnoreCase(url.getProtocol())) {
                    return new File(url.toURI()).exists();
                }
            } catch (IOException | URISyntaxException ex) {
                return false;
            }
            return false;
        }
        return false;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return toURL().openConnection().getInputStream();
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return toURL().openConnection().getOutputStream();
    }

    @Override
    public String toString() {
        return MessageFormat.format("AbstractResource [location={0}]", location);
    }

    @Override
    public URL toURL() throws IOException {
        return new URL(location);
    }
}
