package gargoyle.ct.resource.impl;

import gargoyle.ct.resource.internal.LocalResource;

import java.io.IOException;
import java.net.URL;

public final class CTConfigResource extends LocalResource {

    private CTConfigResource(URL url) {
        super(url);
    }

    @SuppressWarnings("MethodOverridesStaticMethodOfSuperclass")
    public static CTConfigResource findLocal(String name) {
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
}
