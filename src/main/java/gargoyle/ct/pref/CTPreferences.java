package gargoyle.ct.pref;

import gargoyle.ct.pref.impl.prop.CTPrefProperty;

import java.util.Locale;
import java.util.Objects;

public interface CTPreferences extends CTPreferencesManager {

    String BLOCK              = "block";
    int    OPACITY_PERCENT    = 100;
    String TRANSPARENCY       = "transparency";
    String TRANSPARENCY_LEVEL = "transparency-level";

    CTPrefProperty<Boolean> block();

    CTPrefProperty<SUPPORTED_LOCALES> supportedLocales();

    CTPrefProperty<Boolean> transparency();

    CTPrefProperty<Integer> transparencyLevel();

    enum SUPPORTED_LOCALES {
        EN(Locale.ENGLISH),
        RU(new Locale("ru", "RU")); //NON-NLS
        private final Locale locale;

        SUPPORTED_LOCALES(Locale locale) {
            this.locale = locale;
        }

        public static SUPPORTED_LOCALES findSimilar() {
            return findSimilar(Locale.getDefault(), EN);
        }

        public static SUPPORTED_LOCALES findSimilar(Locale locale, SUPPORTED_LOCALES def) {
            for (SUPPORTED_LOCALES value : values()) {
                if (isSimilar(value.locale, locale)) {
                    return value;
                }
            }
            return def;
        }

        private static boolean isSimilar(Locale locale1, Locale locale2) {
            return Objects.equals(locale1, locale2);
        }

        public static SUPPORTED_LOCALES forLocale(Locale locale) {
            for (SUPPORTED_LOCALES value : values()) {
                if (Objects.equals(value.locale, locale)) {
                    return value;
                }
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
