package gargoyle.ct.resource.internal;

import gargoyle.ct.resource.Resource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.MessageFormat;

/**
 * base functionality for {@link Resource}
 */
abstract class AbstractResource extends AbstractResourceBase implements Resource {
    private final String location;

    protected AbstractResource(String location) {
        this.location = location;
    }

    @Nullable
    protected abstract <R extends Resource> R createResource(R base, String location);

    @Override
    public boolean exists() {
        try {
            URL url = toURL();
            return url != null && exists(url);
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URL url = toURL();
        if (url == null) throw new IOException("url is null");
        else return getInputStream(url);
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        URL url = toURL();
        if (url == null) throw new IOException("url is null");
        return getOutputStream(url, true);
    }

    @Override
    public boolean isBase() {
        return true;
    }

    @Override
    public boolean isReadable() {
        try {
            URL url = toURL();
            return url != null && isReadable(url);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean isWritable() {
        try {
            URL url = toURL();
            return url != null && isWritable(url);
        } catch (IOException e) {
            return false;
        }
    }

    @Nullable
    @Override
    public URL toURL() throws IOException {
        return new URL(location);
    }

    @NotNull
    @Override
    public String toString() {
        return MessageFormat.format("{1} [location={0}]", location, getClass().getSimpleName());
    }
}
