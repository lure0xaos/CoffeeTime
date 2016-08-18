package gargoyle.ct;

import java.util.Locale;

public class LocaleProvider {
    private Locale locale;

    public LocaleProvider() {
        this.locale = Locale.getDefault();
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }
}
