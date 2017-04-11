package gargoyle.ct.messages;

import gargoyle.ct.pref.CTPreferences.SUPPORTED_LOCALES;
import gargoyle.ct.prop.CTObservableProperty;

import java.util.Locale;

public interface LocaleProvider {
    Locale getLocale();

    void setLocale(Locale locale);

    CTObservableProperty<SUPPORTED_LOCALES> locale();
}
