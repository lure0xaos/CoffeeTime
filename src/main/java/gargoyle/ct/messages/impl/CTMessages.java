package gargoyle.ct.messages.impl;

import gargoyle.ct.messages.LocaleProvider;
import gargoyle.ct.messages.MessageProvider;

import java.text.MessageFormat;
import java.util.*;
import java.util.ResourceBundle.Control;

public class CTMessages implements MessageProvider {
    private static final String MSG_NO_MESSAGE = "Can''t find resource for bundle {0}, key {1}";
    private static final String CAN_T_PARSE_MESSAGE_0_1_2 = "can''t parse message:{0}->{1}({2})";
    private final LocaleProvider localeProvider = new LocaleProvider();
    private ResourceBundle messages;

    public CTMessages(String baseName) {
        this(baseName, Locale.getDefault());
    }

    private CTMessages(String baseName, Locale locale) {
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
        try {
            String pattern = messages.getString(message);
            try {
                return MessageFormat.format(pattern, args);
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException(
                        MessageFormat.format(CAN_T_PARSE_MESSAGE_0_1_2, message, pattern, Arrays.toString(args)), ex);
            }
        } catch (MissingResourceException ex) {
            String key = ex.getKey();
            String bundle = messages.getBaseBundleName();
            throw new MissingResourceException(MessageFormat.format(MSG_NO_MESSAGE, bundle, key), bundle, key);
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
