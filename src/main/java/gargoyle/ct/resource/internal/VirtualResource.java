package gargoyle.ct.resource.internal;

import gargoyle.ct.resource.Resource;
import gargoyle.ct.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle.Control;

public class VirtualResource extends AbstractResource {
    private static final char CHAR_DOT = '.';
    private final Resource baseResource;
    private Locale locale;

    protected VirtualResource(Resource baseResource, String location) {
        super(location);
        this.baseResource = baseResource;
    }

    protected VirtualResource(String location) {
        super(location);
        baseResource = this;
    }

    @Override
    protected VirtualResource createResource(Resource base, String location) {
        return base == null ? new VirtualResource(location) : new VirtualResource(base, location);
    }

    @Override
    public Resource forLocale(Locale locale) {
        Control ctrl = Control.getControl(Control.FORMAT_DEFAULT);
        String location = baseResource.getLocation();
        int i = location.lastIndexOf(CHAR_DOT);
        String baseName = location.substring(0, i);
        String suffix = location.substring(i + 1);
        for (Locale specificLocale : ctrl.getCandidateLocales(baseName, locale)) {
            String loc = ctrl.toResourceName(ctrl.toBundleName(baseName, specificLocale), suffix);
            Log.debug("trying location {0}", loc);
            VirtualResource resource = createResource(baseResource, loc);
            if (resource.exists()) {
                resource.locale = locale;
                return resource;
            }
        }
        Log.debug("{0} not found, returning null");
        return null;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    public String getRelativeLocation() {
        String thisLocation = getLocation();
        String baseLocation = baseResource.getLocation();
        int minLength = Math.min(thisLocation.length(), baseLocation.length());
        for (int i = 0; i < minLength; i++) {
            if (thisLocation.charAt(i) != baseLocation.charAt(i)) {
                return thisLocation.substring(i);
            }
        }
        return thisLocation;
    }

    @Override
    public URL toURL() throws IOException {
        return Objects.equals(baseResource, this) ? super.toURL() : new URL(baseResource.toURL(), getLocation());
    }

    @Override
    public Resource withExtension(String extension) {
        String location = getLocation();
        String
                loc =
                new StringBuilder().append(location.substring(0, location.lastIndexOf(CHAR_DOT)))
                        .append(".")
                        .append(extension)
                        .toString();
        return createResource(null, loc);
    }
}
