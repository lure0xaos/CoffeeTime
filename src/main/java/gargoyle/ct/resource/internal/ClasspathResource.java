package gargoyle.ct.resource.internal;

import gargoyle.ct.resource.Resource;

import java.io.IOException;
import java.net.URL;

/**
 * represents a {@link Resource} withing application class path (ex. in jar file). writing is mainly unsupported
 */
public class ClasspathResource extends VirtualResource {
    final ClassLoader loader;

    public ClasspathResource(String location) {
        this(ClasspathResource.class.getClassLoader(), location);
    }

    public ClasspathResource(ClassLoader loader, String location) {
        super(location);
        this.loader = loader;
    }

    ClasspathResource(ClassLoader loader, Resource base, String location) {
        super(base, location);
        this.loader = loader;
    }

    @Override
    protected ClasspathResource createResource(Resource base, String location) {
        return base == null ?
                new ClasspathResource(loader, location) :
                new ClasspathResource(getLoader(base), base, location);
    }

    @Override
    public URL toURL() throws IOException {
        return loader.getResource(getLocation());
    }

    ClassLoader getLoader(Resource resource) {
        if (resource instanceof ClasspathResource) {
            return ((ClasspathResource) resource).loader;
        }
        return loader;
    }

    @Override
    public boolean exists() {
        try {
            return exists(toURL());
        } catch (IOException ex) {
            return false;
        }
    }
}
