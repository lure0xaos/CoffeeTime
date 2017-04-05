package gargoyle.ct.resource.internal;

import gargoyle.ct.util.Log;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

public class LocalResource extends VirtualResource {
    private static final String CANNOT_CREATE_ROOTS = "Cannot create roots";
    private static final String CANNOT_USE_0_AS_ROOT = "Cannot use {0} as root";
    private static final String USER_DIR = "user.dir";
    private static final String USER_HOME = "user.home";

    protected LocalResource(URL url) {
        super(url.toExternalForm());
    }

    protected static LocalResource findLocal(String name) {
        for (URL root : getLocations()) {
            if (root == null) {
                Log.warn("some location is null");
                continue;
            }
            try {
                LocalResource resource = new LocalResource(new URL(root, name));
                if (resource.exists()) {
                    return resource;
                }
            } catch (MalformedURLException ex) {
                throw new RuntimeException(MessageFormat.format(CANNOT_USE_0_AS_ROOT, root), ex);
            }
        }
        return null;
    }

    public static URL[] getLocations() {
        try {
            return new URL[]{getCurrentDirectoryLocation(),
                    getProgramDirectoryLocation(),
                    getHomeDirectoryLocation(),
                    getClasspathLocation()};
        } catch (RuntimeException ex) {
            throw new RuntimeException(CANNOT_CREATE_ROOTS, ex);
        }
    }

    public static URL getClasspathLocation() {
        return LocalResource.class.getResource("/");
    }

    public static URL getHomeDirectoryLocation() {
        try {
            return new File(System.getProperty(USER_HOME, ".")).toURI().toURL();
        } catch (MalformedURLException ex) {
            throw new RuntimeException(MessageFormat.format(CANNOT_USE_0_AS_ROOT, USER_HOME), ex);
        }
    }

    public static URL getProgramDirectoryLocation() {
        try {
            return new File(System.getProperty(USER_DIR, ".")).toURI().toURL();
        } catch (MalformedURLException ex) {
            throw new RuntimeException(MessageFormat.format(CANNOT_USE_0_AS_ROOT, USER_DIR), ex);
        }
    }

    public static URL getCurrentDirectoryLocation() {
        try {
            return new File(".").toURI().toURL();
        } catch (MalformedURLException ex) {
            throw new RuntimeException(MessageFormat.format(CANNOT_USE_0_AS_ROOT, "."), ex);
        }
    }
}
