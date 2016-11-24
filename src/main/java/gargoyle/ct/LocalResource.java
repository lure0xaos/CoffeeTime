package gargoyle.ct;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class LocalResource extends VirtualResource {

    protected LocalResource(URL url) {
        super(url.toExternalForm());
    }

    protected static LocalResource findLocal(String name) {
        for (URL root : getLocations()) {
            try {
                LocalResource resource = new LocalResource(new URL(root, name));
                if (resource.exists()) {
                    return resource;
                }
            } catch (MalformedURLException ex) {
                throw new RuntimeException("Cannot use " + root + " as root", ex);
            }
        }
        return null;
    }

    private static URL[] getLocations() {
        try {
            return new URL[]{new File(".").toURI().toURL(),
                new File(System.getProperty("user.dir", ".")).toURI().toURL(),
                new File(System.getProperty("user.home", ".")).toURI().toURL()};
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Cannot create roots", ex);
        }
    }
}
