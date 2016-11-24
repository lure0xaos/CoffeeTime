package gargoyle.ct;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

public class CTMessageProvider implements MessageProvider {

    private final LocaleProvider localeProvider = new LocaleProvider();

    private ResourceBundle messages;

    public CTMessageProvider(String baseName) {
        this(baseName, Locale.getDefault());
    }

    private CTMessageProvider(String baseName, Locale locale) {
        localeProvider.setLocale(locale);
        load(baseName);
    }

    private void check() {
        if (!Objects.equals(messages.getLocale(), localeProvider.getLocale())) {
            reload();
        }
    }

    public Locale getLocale() {
        return localeProvider.getLocale();
    }

    public void setLocale(Locale locale) {
        localeProvider.setLocale(locale);
        reload();
    }

    @Override
    public String getMessage(String message, Object... args) {
        check();
        String pattern = messages.getString(message);
        try {
            return MessageFormat.format(pattern, args);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(
                "can't parse message:" + message + "->" + pattern + "(" + Arrays.toString(args) + ")", ex);
        }
    }

    private void load(String baseName) {
        messages =
            ResourceBundle.getBundle(baseName, localeProvider.getLocale(),
                Control.getControl(Control.FORMAT_PROPERTIES));
    }

    private void reload() {
        load(messages.getBaseBundleName());
    }
}
