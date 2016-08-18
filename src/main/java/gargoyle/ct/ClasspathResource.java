package gargoyle.ct;

import java.io.IOException;
import java.net.URL;

public class ClasspathResource extends VirtualResource {
    private ClasspathResource(final Resource base, final String location) {
        super(base, location);
    }

    public ClasspathResource(final String location) {
        super(location);
    }

    @Override
    protected ClasspathResource createResource(final Resource base, final String location) {
        return base == null ? new ClasspathResource(location) : new ClasspathResource(base, location);
    }

    @Override
    public boolean exists() {
        try {
            return this.exists(this.toURL());
        } catch (final IOException ex) {
            return false;
        }
    }

    @Override
    public URL toURL() throws IOException {
        return Thread.currentThread().getContextClassLoader().getResource(this.getLocation());
    }
}
