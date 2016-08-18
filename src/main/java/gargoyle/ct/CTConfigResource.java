package gargoyle.ct;

import java.io.IOException;
import java.net.URL;

public class CTConfigResource extends LocalResource {
    public static CTConfigResource findLocal(final String name) {
        final LocalResource local = LocalResource.findLocal(name);
        try {
            return (local != null) && local.exists() ? new CTConfigResource(local.toURL()) : null;
        } catch (final IOException ex) {
            return null;
        }
    }

    public static CTConfigResource forURL(final URL url) {
        return new CTConfigResource(url);
    }

    private CTConfigResource(final URL url) {
        super(url);
    }
}
