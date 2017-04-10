package gargoyle.ct.pref;

import gargoyle.ct.pref.impl.prop.CTPrefProperty;

import java.util.Locale;

public interface CTPreferences extends CTPreferencesManager {
    String BLOCK = "block";
    String TRANSPARENCY = "transparency";
    String TRANSPARENCY_LEVEL = "transparency-level";

    CTPrefProperty<Boolean> block();

    CTPrefProperty<SUPPORTED_LOCALES> supportedLocales();

    CTPrefProperty<Boolean> transparency();

    CTPrefProperty<Float> transparencyLevel();

    enum SUPPORTED_LOCALES {
        DEFAULT(Locale.getDefault()), RU(new Locale("ru", "RU"));
        private final Locale locale;

        SUPPORTED_LOCALES(Locale locale) {
            this.locale = locale;
        }

        public static SUPPORTED_LOCALES forLocale(Locale locale) {
            for (SUPPORTED_LOCALES value : values()) {
                if (value.locale.equals(locale))
                    return value;
            }
            return null;
        }

        public Locale getLocale() {
            return locale;
        }

        @Override
        public String toString() {
            return locale.getDisplayName();
        }
    }
}
