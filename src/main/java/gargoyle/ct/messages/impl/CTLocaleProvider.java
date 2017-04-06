package gargoyle.ct.messages.impl;

import gargoyle.ct.messages.LocaleProvider;

import java.util.Locale;

public final class CTLocaleProvider implements LocaleProvider {
    private static CTLocaleProvider instance;
    private Locale locale;

    private CTLocaleProvider(Locale locale) {
        this.locale = locale;
    }

    public static LocaleProvider getInstance() {
        if (instance == null) {
            synchronized (LocaleProvider.class) {
                if (instance == null) {
                    instance = new CTLocaleProvider(Locale.getDefault());
                }
            }
        }
        return instance;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
