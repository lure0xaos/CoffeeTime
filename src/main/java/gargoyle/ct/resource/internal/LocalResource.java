package gargoyle.ct.resource.internal;

import gargoyle.ct.resource.Resource;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Comparator;

/**
 * local filesystem {@link Resource} from different locations
 */
public class LocalResource extends VirtualResource {
    private static final String ENV_USER_DIR = "user.dir";
    private static final String ENV_USER_HOME = "user.home";
    private static final String MSG_CANNOT_CREATE_ROOTS = "Cannot create roots";
    private static final String MSG_CANNOT_USE_0_AS_ROOT = "Cannot use {0} as root";

    protected LocalResource(URL url) {
        super(url.toExternalForm());
    }

    @SuppressWarnings("ObjectAllocationInLoop")
    protected static LocalResource findLocal(String name) {
        LocalResource writable = null;
        int writablePriority = Integer.MIN_VALUE;
        for (LocalLocation root : getReadableLocations()) {
            if (root != null && root.getUrl() != null) {
                try {
                    LocalResource resource = new LocalResource(new URL(root.getUrl(), name));
                    if (resource.exists()) {
                        return resource;
                    }
                    int writePriority = root.getWritePriority();
                    if (resource.isWritable() && (writable == null || writablePriority < writePriority)) {
                        writable = resource;
                        writablePriority = writePriority;
                    }
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(MessageFormat.format(MSG_CANNOT_USE_0_AS_ROOT, root), ex);
                }
            }
        }
        return writable;
    }

    private static LocalLocation[] getReadableLocations() {
        return getLocations();
    }

    private static LocalLocation[] getLocations() {
        try {
            return new LocalLocation[]{
                    new LocalLocation(new File(".").toURI().toURL(), 0, 0),
                    new LocalLocation(new File(System.getProperty(ENV_USER_DIR, DOT)).toURI().toURL(), 0, 0),
                    new LocalLocation(getHomeDirectoryLocation(), 0, 1),
                    new LocalLocation(LocalResource.class.getResource("/"), 0, 0)
            };
        } catch (MalformedURLException ex) {
            throw new RuntimeException(MSG_CANNOT_CREATE_ROOTS, ex);
        }
    }

    private static LocalLocation[] getWritableLocations() {
        return getLocations();
    }

    public static URL getHomeDirectoryLocation() {
        try {
            return new File(System.getProperty(ENV_USER_HOME, DOT)).toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }

    public File toFile() throws IOException {
        URL url = toURL();
        String path = url.getPath();
        return new File(path.startsWith(File.separator) ? path.substring(1) : path); // XXX hacky
    }

    private static class LocalLocation {
        private final int readPriority;
        private final URL url;
        private final int writePriority;

        public LocalLocation(URL url, int readPriority, int writePriority) {
            this.url = url;
            this.readPriority = readPriority;
            this.writePriority = writePriority;
        }

        public int getReadPriority() {
            return readPriority;
        }

        public URL getUrl() {
            return url;
        }

        public int getWritePriority() {
            return writePriority;
        }

        @Override
        public int hashCode() {
            return url.toExternalForm().hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LocalLocation that = (LocalLocation) o;
            return url.toExternalForm().equals(that.url.toExternalForm());
        }
    }

    private static class LocalLocationReadPriority implements Comparator<LocalLocation>, Serializable {
        private static final long serialVersionUID = 3293380242047042026L;

        @Override
        public int compare(LocalLocation o1, LocalLocation o2) {
            return Integer.compare(o1.getReadPriority(), o2.getReadPriority());
        }
    }

    private static class LocalLocationWritePriority implements Comparator<LocalLocation>, Serializable {
        private static final long serialVersionUID = 1771423086301596535L;

        @Override
        public int compare(LocalLocation o1, LocalLocation o2) {
            return Integer.compare(o1.getWritePriority(), o2.getWritePriority());
        }
    }
}
