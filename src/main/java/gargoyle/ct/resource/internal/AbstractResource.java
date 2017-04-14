package gargoyle.ct.resource.internal;

import gargoyle.ct.log.Log;
import gargoyle.ct.resource.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.jar.JarFile;

abstract class AbstractResource implements Resource {

    private static final String CHECK_IF_EXISTS_0_1_AT_2 = "check if exists {0}({1}) at {2}";
    private static final String METHOD_HEAD              = "HEAD";
    private static final String MSG_FOUND                = "{0} found";
    private static final String MSG_NOT_FOUND            = "{0} not found";
    private static final String PROP_UA                  = "User-Agent";
    private static final String PROTOCOL_FILE            = "file";
    private static final String SCHEME_FILE              = PROTOCOL_FILE;
    private static final String USER_AGENT               =
            "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; " + "rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR " +
            "3.5.30729)";
    private final String location;

    protected AbstractResource(String location) {
        this.location = location;
    }

    @Override
    public boolean exists() {
        try {
            return exists(toURL());
        } catch (IOException ex) {
            return false;
        }
    }

    @SuppressWarnings("ChainOfInstanceofChecks")
    boolean exists(URL url) {
        if (url != null) {
            try {
                URLConnection connection = url.openConnection();
                Log.debug(CHECK_IF_EXISTS_0_1_AT_2, connection, url.getProtocol(), url);
                if (connection instanceof HttpURLConnection) {
                    HttpURLConnection huc = (HttpURLConnection) connection;
                    huc.setInstanceFollowRedirects(false);
                    huc.setRequestMethod(METHOD_HEAD);
                    huc.setRequestProperty(PROP_UA, USER_AGENT);
                    huc.connect();
                    boolean exists = huc.getResponseCode() == HttpURLConnection.HTTP_OK;
                    if (exists) {
                        Log.debug(MSG_FOUND, url);
                    }
                    return exists;
                }
                if (connection instanceof JarURLConnection) {
                    JarURLConnection juc = (JarURLConnection) connection;
                    juc.setRequestProperty(PROP_UA, USER_AGENT);
                    juc.connect();
                    try (JarFile jar = juc.getJarFile()) {
                        if (jar != null) {
                            Log.debug(MSG_FOUND, url);
                        }
                    }
                    return false;
                }
                if (SCHEME_FILE.equalsIgnoreCase(url.getProtocol())) {
                    boolean exists = new File(url.toURI()).exists();
                    if (exists) {
                        Log.debug(MSG_FOUND, url);
                    }
                    return exists;
                }
            } catch (IOException | URISyntaxException ex) {
                Log.debug(MSG_NOT_FOUND, url);
                return false;
            }
            Log.debug(MSG_NOT_FOUND, url);
            return false;
        }
        Log.debug(MSG_NOT_FOUND, "");
        return false;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URL url = toURL();
        return url.openConnection().getInputStream();
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        URL    url      = toURL();
        String protocol = url.getProtocol();
        if (PROTOCOL_FILE.equals(protocol)) {
            return new FileOutputStream(location.startsWith(PROTOCOL_FILE + ":/") ?
                                        location.substring(protocol.length() + 2) :
                                        location);
        }
        return url.openConnection().getOutputStream();
    }

    @Override
    public URL toURL() throws IOException {
        return new URL(location);
    }

    @Override
    public String toString() {
        return MessageFormat.format("AbstractResource [location={0}]", location);
    }

    protected abstract <R extends Resource> R createResource(R base, String location);
}
