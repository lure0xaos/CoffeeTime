package gargoyle.ct.messages.impl;

import gargoyle.ct.log.Log;
import gargoyle.ct.messages.LocaleProvider;
import gargoyle.ct.messages.MessageProvider;
import gargoyle.ct.messages.MessageProviderEx;
import gargoyle.ct.messages.util.UTF8Control;
import gargoyle.ct.pref.CTPreferences.SUPPORTED_LOCALES;
import gargoyle.ct.prop.CTObservableProperty;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CTMessages implements MessageProviderEx {
    private static final String CAN_T_PARSE_MESSAGE_0_1_2 = "can''t parse message:{0}->{1}({2})";
    private static final String MSG_NO_BUNDLE = "Can''t find bundle {0}";
    private static final String MSG_NO_MESSAGE = "Can''t find resource for bundle {0}, key {1}";
    private final LocaleProvider localeProvider;
    private final MessageProvider parent;
    private ResourceBundle messages;

    public CTMessages(String baseName) {
        this(null, baseName);
    }

    public CTMessages(MessageProvider parent, String baseName) {
        localeProvider = new CTLocaleProvider();
        this.parent = parent;
        load(baseName);
        locale().addPropertyChangeListener(event -> reload());
    }

    private void load(String baseName) {
        try {
            messages = ResourceBundle.getBundle(baseName, localeProvider.getLocale(), UTF8Control.getControl());
        } catch (MissingResourceException ex) {
            if (parent == null) {
                throw new MissingResourceException(MessageFormat.format(MSG_NO_BUNDLE, baseName), baseName, ex.getKey());
            } else {
                Log.warn(MessageFormat.format(MSG_NO_BUNDLE, messages.getBaseBundleName()));
            }
        }
    }

    private void reload() {
        load(messages.getBaseBundleName());
    }

    @Override
    public Locale getLocale() {
        return localeProvider.getLocale();
    }

    @Override
    public void setLocale(Locale locale) {
        localeProvider.setLocale(locale);
    }

    @Override
    public CTObservableProperty<SUPPORTED_LOCALES> locale() {
        return localeProvider.locale();
    }

    @Override
    public String getMessage(String message, Object... args) {
        if (!Objects.equals(messages.getLocale(), localeProvider.getLocale())) {
            reload();
        }
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
}
