package gargoyle.ct;

import java.io.IOException;
import java.net.URL;

public class ClasspathResource extends VirtualResource {

    private ClasspathResource(Resource base, String location) {
        super(base, location);
    }

    public ClasspathResource(String location) {
        super(location);
    }

    @Override
    protected ClasspathResource createResource(Resource base, String location) {
        return base == null ? new ClasspathResource(location) : new ClasspathResource(base, location);
    }

    @Override
    public boolean exists() {
        try {
            return exists(toURL());
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public URL toURL() throws IOException {
        return Thread.currentThread().getContextClassLoader().getResource(getLocation());
    }
}
