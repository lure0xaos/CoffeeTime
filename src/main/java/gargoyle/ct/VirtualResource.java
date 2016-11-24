package gargoyle.ct;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle.Control;

public class VirtualResource extends AbstractResource {

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
        int i = location.lastIndexOf('.');
        String baseName = location.substring(0, i);
        String suffix = location.substring(i + 1);
        for (Locale specificLocale : ctrl.getCandidateLocales(baseName, locale)) {
            String loc = ctrl.toResourceName(ctrl.toBundleName(baseName, specificLocale), suffix);
            VirtualResource resource = createResource(baseResource, loc);
            if (resource.exists()) {
                resource.locale = locale;
                return resource;
            }
        }
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
        String loc = location.substring(0, location.lastIndexOf('.')) + "." + extension;
        return createResource(null, loc);
    }
}
