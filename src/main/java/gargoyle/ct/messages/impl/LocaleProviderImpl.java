package gargoyle.ct.messages.impl;

import gargoyle.ct.messages.LocaleProvider;

import java.util.Locale;

public final class LocaleProviderImpl implements LocaleProvider {
    private static LocaleProviderImpl instance;
    private Locale locale;

    private LocaleProviderImpl(Locale locale) {
        this.locale = locale;
    }

    public static LocaleProvider getInstance() {
        if (instance == null) {
            synchronized (LocaleProvider.class) {
                if (instance == null) {
                    instance = new LocaleProviderImpl(Locale.getDefault());
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
