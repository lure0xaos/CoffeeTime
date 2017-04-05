package gargoyle.ct.messages;

import java.util.Locale;

public interface LocaleProvider {
    Locale getLocale();

    void setLocale(Locale locale);
}
