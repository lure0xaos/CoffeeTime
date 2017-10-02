package gargoyle.ct.pref;

import gargoyle.ct.config.CTConfig;
import gargoyle.ct.messages.Described;
import gargoyle.ct.pref.impl.prop.CTPrefProperty;

import java.util.Locale;
import java.util.Objects;

public interface CTPreferences extends CTPreferencesManager {
    String PREF_BLOCK = "block";
    String PREF_CONFIG = "config";
    String PREF_ICON_STYLE = "icon-style";
    int OPACITY_PERCENT = 100;
    String PREF_SOUND = "sound";
    String PREF_TRANSPARENCY = "transparency";
    String PREF_TRANSPARENCY_LEVEL = "transparency-level";

    CTPrefProperty<Boolean> block();

    CTPrefProperty<CTConfig> config();

    CTPrefProperty<ICON_STYLE> iconStyle();

    CTPrefProperty<Boolean> sound();

    CTPrefProperty<SUPPORTED_LOCALES> supportedLocales();

    CTPrefProperty<Boolean> transparency();

    CTPrefProperty<Integer> transparencyLevel();

    enum ICON_STYLE implements Described {
        BW("bw", "icon-style.bw"),
        WB("wb", "icon-style.wb");
        private final String description;
        private final String path;

        ICON_STYLE(String path, String description) {
            this.path = path;
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }

        public String getPath() {
            return path;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    enum SUPPORTED_LOCALES implements Described {
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
        public String getDescription() {
            return locale.getDisplayName();
        }
    }
}
