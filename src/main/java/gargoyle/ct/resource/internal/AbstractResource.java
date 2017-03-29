package gargoyle.ct.resource.internal;

import gargoyle.ct.resource.Resource;
import gargoyle.ct.util.Log;

import java.io.*;
import java.net.*;
import java.text.MessageFormat;

abstract class AbstractResource implements Resource {

    public static final String PROTOCOL_FILE = "file";
    public static final String COLON_SLASH = ":/";
    private static final String METHOD_HEAD = "HEAD";
    private static final String PROP_UA = "User-Agent";
    private static final String SCHEME_FILE = PROTOCOL_FILE;
    private static final String
            USER_AGENT =
            "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)";
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
                Log.debug("check if exists {0}({1}) at {2}", connection, url.getProtocol(), url);
                if (connection instanceof HttpURLConnection) {
                    HttpURLConnection huc = (HttpURLConnection) connection;
                    huc.setInstanceFollowRedirects(false);
                    huc.setRequestMethod(METHOD_HEAD);
                    huc.setRequestProperty(PROP_UA, USER_AGENT);
                    huc.connect();
                    boolean exists = huc.getResponseCode() == HttpURLConnection.HTTP_OK;
                    if (exists) {
                        Log.debug("{0} found", url);
                    }
                    return exists;
                }
                if (connection instanceof JarURLConnection) {
                    JarURLConnection juc = (JarURLConnection) connection;
                    juc.setRequestProperty(PROP_UA, USER_AGENT);
                    juc.connect();
                    boolean exists = juc.getJarFile() != null;
                    if (exists) {
                        Log.debug("{0} found", url);
                    }
                    return exists;
                }
                //noinspection CallToStringEqualsIgnoreCase
                if (SCHEME_FILE.equalsIgnoreCase(url.getProtocol())) {
                    boolean exists = new File(url.toURI()).exists();
                    if (exists) {
                        Log.debug("{0} found", url);
                    }
                    return exists;
                }
            } catch (IOException | URISyntaxException ex) {
                Log.debug("{0} not found", url);
                return false;
            }
            Log.debug("{0} not found", url);
            return false;
        }
        Log.debug("{0} not found", url);
        return false;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URL url = toURL();
//        String protocol = url.getProtocol();
//        if (PROTOCOL_FILE.equals(protocol)) {
//            return new FileInputStream(location.startsWith(PROTOCOL_FILE + COLON_SLASH) ? location.substring(protocol.length() + 2) : location);
//        }
        return url.openConnection().getInputStream();
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        URL url = toURL();
        String protocol = url.getProtocol();
        if (PROTOCOL_FILE.equals(protocol)) {
            return new FileOutputStream(location.startsWith(PROTOCOL_FILE + ":/") ? location.substring(protocol.length() + 2) : location);
        }
        return url.openConnection().getOutputStream();
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
