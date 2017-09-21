package gargoyle.ct.messages.impl;

import gargoyle.ct.messages.LocaleProvider;
import gargoyle.ct.pref.CTPreferences;
import gargoyle.ct.pref.CTPreferences.SUPPORTED_LOCALES;
import gargoyle.ct.prop.CTObservableProperty;

import java.util.Locale;

public final class CTPreferencesLocaleProvider implements LocaleProvider {
    private final CTObservableProperty<SUPPORTED_LOCALES> locale;

    public CTPreferencesLocaleProvider(CTPreferences preferences) {
        this(preferences.supportedLocales());
    }

    private CTPreferencesLocaleProvider(CTObservableProperty<SUPPORTED_LOCALES> locale) {
        this.locale = locale;
    }

    @Override
    public Locale getLocale() {
        return locale.get().getLocale();
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale.set(SUPPORTED_LOCALES.forLocale(locale));
    }

    @Override
    public CTObservableProperty<SUPPORTED_LOCALES> locale() {
        return locale;
    }
}
