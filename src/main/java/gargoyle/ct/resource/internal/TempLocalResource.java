package gargoyle.ct.resource.internal;

import gargoyle.ct.resource.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class TempLocalResource extends ClasspathResource {
    private TempLocalResource(ClassLoader loader, Resource base, String location) {
        super(loader, base, location);
    }

    public TempLocalResource(String location) {
        super(location);
    }

    @Override
    protected TempLocalResource createResource(Resource base, String location) {
        ClassLoader loader = getLoader(base);
        Resource resource = base == null ?
                new ClasspathResource(location) : new ClasspathResource(loader, base, location);
        if (!resource.exists()) {
            return new TempLocalResource(location);
        }
        try (InputStream stream = resource.getInputStream()) {
            File tempFile = File.createTempFile(getBaseName(), DOT + getExtension());
            Files.copy(stream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            tempFile.deleteOnExit();
            return new TempLocalResource(loader, resource, tempFile.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public URL toURL() throws IOException {
        return new File(getLocation()).toURI().toURL();
    }

    @Override
    public boolean exists() {
        try {
            return exists(toURL());
        } catch (IOException ex) {
            return false;
        }
    }

    public File toFile() throws IOException {
        return new File(toURL().getPath().substring(1));
    }
}
