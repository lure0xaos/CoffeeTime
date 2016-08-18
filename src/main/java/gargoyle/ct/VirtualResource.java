package gargoyle.ct;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class VirtualResource extends AbstractResource {
    private final Resource baseResource;
    private Locale locale;

    protected VirtualResource(final Resource baseResource, final String location) {
        super(location);
        this.baseResource = baseResource;
    }

    protected VirtualResource(final String location) {
        super(location);
        this.baseResource = this;
    }

    @Override
    protected VirtualResource createResource(final Resource base, final String location) {
        return base == null ? new VirtualResource(location) : new VirtualResource(base, location);
    }

    @Override
    public Resource forLocale(final Locale locale) {
        final ResourceBundle.Control ctrl = ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_DEFAULT);
        final String location = this.baseResource.getLocation();
        final int i = location.lastIndexOf('.');
        final String baseName = location.substring(0, i);
        final String suffix = location.substring(i + 1);
        for (final Locale specificLocale : ctrl.getCandidateLocales(baseName, locale)) {
            final String loc = ctrl.toResourceName(ctrl.toBundleName(baseName, specificLocale), suffix);
            final VirtualResource resource = this.createResource(this.baseResource, loc);
            if (resource.exists()) {
                resource.locale = locale;
                return resource;
            }
        }
        return null;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public String getRelativeLocation() {
        final String thisLocation = this.getLocation();
        final String baseLocation = this.baseResource.getLocation();
        final int minLength = Math.min(thisLocation.length(), baseLocation.length());
        for (int i = 0; i < minLength; i++) {
            if (thisLocation.charAt(i) != baseLocation.charAt(i)) {
                return thisLocation.substring(i);
            }
        }
        return thisLocation;
    }

    @Override
    public URL toURL() throws IOException {
        return this.baseResource == this ? super.toURL() : new URL(this.baseResource.toURL(), this.getLocation());
    }

    @Override
    public Resource withExtension(final String extension) {
        final String location = this.getLocation();
        final String loc = location.substring(0, location.lastIndexOf('.')) + "." + extension;
        return this.createResource(null, loc);
    }
}
