package gargoyle.ct.resource.impl;

import gargoyle.ct.resource.internal.LocalResource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public final class CTConfigResource extends LocalResource {
    private static final String SLASH = "/";

    private CTConfigResource(URL url) {
        super(url);
    }

    public static CTConfigResource findLocalConfig(String name) {
        LocalResource local = LocalResource.findLocal(name);
        try {
            return local != null && local.exists() ? new CTConfigResource(local.toURL()) : null;
        } catch (IOException ex) {
            return null;
        }
    }

    public static CTConfigResource forURL(URL url) {
        return new CTConfigResource(url);
    }

    public static CTConfigResource forURL(URL root, String file) {
        try {
            String rootString = root.toExternalForm();
            return new CTConfigResource(new URL((rootString.endsWith(SLASH) ? rootString : rootString + SLASH) + file));
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
