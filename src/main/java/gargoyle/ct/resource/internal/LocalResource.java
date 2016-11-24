package gargoyle.ct.resource.internal;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

public class LocalResource extends VirtualResource {

    private static final String USER_DIR = "user.dir";

    private static final String USER_HOME = "user.home";

    private static final String CANNOT_USE_0_AS_ROOT = "Cannot use {0} as root";

    private static final String CANNOT_CREATE_ROOTS = "Cannot create roots";

    protected LocalResource(URL url) {
        super(url.toExternalForm());
    }

    @SuppressWarnings("ObjectAllocationInLoop")
    protected static LocalResource findLocal(String name) {
        for (URL root : getLocations()) {
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

    private static URL[] getLocations() {
        try {
            return new URL[]{new File(".").toURI().toURL(),
                new File(System.getProperty(USER_DIR, ".")).toURI().toURL(),
                new File(System.getProperty(USER_HOME, ".")).toURI().toURL()};
        } catch (MalformedURLException ex) {
            throw new RuntimeException(CANNOT_CREATE_ROOTS, ex);
        }
    }
}
