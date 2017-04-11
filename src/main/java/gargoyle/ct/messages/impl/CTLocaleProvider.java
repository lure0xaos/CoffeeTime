package gargoyle.ct.messages.impl;

import gargoyle.ct.messages.LocaleProvider;
import gargoyle.ct.pref.CTPreferences.SUPPORTED_LOCALES;
import gargoyle.ct.prop.CTProperty;
import gargoyle.ct.prop.impl.CTSimpleEnumProperty;

import java.util.Locale;

public final class CTLocaleProvider implements LocaleProvider {
    private final CTProperty<SUPPORTED_LOCALES> locale;

    public CTLocaleProvider() {
        this(new CTSimpleEnumProperty<>(SUPPORTED_LOCALES.DEFAULT));
    }

    public CTLocaleProvider(CTProperty<SUPPORTED_LOCALES> locale) {
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
}
