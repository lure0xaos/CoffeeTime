package gargoyle.ct.messages.impl;

import gargoyle.ct.messages.LocaleProvider;

import java.util.Locale;

public final class CTLocaleProvider implements LocaleProvider {
    private static Locale locale;

    static {
        locale = Locale.getDefault();
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void setLocale(Locale locale) {
        CTLocaleProvider.locale = locale;
    }
}
