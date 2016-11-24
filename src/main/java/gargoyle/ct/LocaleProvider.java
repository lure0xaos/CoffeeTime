package gargoyle.ct;

import java.util.Locale;

public class LocaleProvider {

    private Locale locale;

    public LocaleProvider() {
        locale = Locale.getDefault();
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
