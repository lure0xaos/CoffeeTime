package gargoyle.ct.messages.impl;

import gargoyle.ct.log.Log;
import gargoyle.ct.messages.LocaleProvider;
import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.messages.util.UTF8Control;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CTMessages implements MessageProvider {
    private static final String CAN_T_PARSE_MESSAGE_0_1_2 = "can''t parse message:{0}->{1}({2})";
    private static final String MSG_NO_BUNDLE = "Can''t find bundle {0}";
    private static final String MSG_NO_MESSAGE = "Can''t find resource for bundle {0}, key {1}";
    private final LocaleProvider localeProvider;
    private final MessageProvider parent;
    private ResourceBundle messages;

    public CTMessages(String baseName) {
        this(null, baseName, Locale.getDefault());
    }

    public CTMessages(MessageProvider parent, String baseName, Locale locale) {
        localeProvider = CTLocaleProvider.getInstance();
        this.parent = parent;
        localeProvider.setLocale(locale);
        load(baseName);
    }

    private void load(String baseName) {
        try {
            messages = ResourceBundle.getBundle(baseName, getLocale(), UTF8Control.getControl());
        } catch (MissingResourceException ex) {
            if (parent == null) {
                throw new MissingResourceException(MessageFormat.format(MSG_NO_BUNDLE, baseName), baseName, ex.getKey());
            } else {
                Log.warn(MessageFormat.format(MSG_NO_BUNDLE, messages.getBaseBundleName()));
            }
        }
    }

    public Locale getLocale() {
        return localeProvider.getLocale();
    }

    public void setLocale(Locale locale) {
        localeProvider.setLocale(locale);
        reload();
    }

    private void reload() {
        load(messages.getBaseBundleName());
    }

    public CTMessages(String baseName, Locale locale) {
        this(null, baseName, locale);
    }

    public CTMessages(MessageProvider parent, String baseName) {
        this(parent, baseName, Locale.getDefault());
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
            if (parent == null) {
                String bundle = messages.getBaseBundleName();
                throw new MissingResourceException(MessageFormat.format(MSG_NO_MESSAGE, bundle, message), bundle, message);
            } else {
                return parent.getMessage(message, args);
            }
        }
    }

    private void check() {
        if (!Objects.equals(messages.getLocale(), getLocale())) {
            reload();
        }
    }
}
