package gargoyle.ct.resource.impl;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.resource.Resource;
import gargoyle.ct.resource.internal.LocalResource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * {@link CTConfig} as {@link Resource}
 */
public final class CTConfigResource extends LocalResource {
    private static final String SLASH = "/";

    private CTConfigResource(URL url) {
        super(url);
    }

    @SuppressWarnings("MethodOverridesStaticMethodOfSuperclass")
    public static CTConfigResource findLocalConfig(String name, boolean existing) {
        LocalResource local = LocalResource.findLocal(name);
        try {
            return local == null ? null : existing && !local.exists() ? null : new CTConfigResource(local.toURL());
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
