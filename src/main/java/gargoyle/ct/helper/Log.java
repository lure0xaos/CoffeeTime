package gargoyle.ct.helper;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public final class Log {
    private static final String LOCATION_ERRORS = "messages/errors";
    private static final String LOGGING_PROPERTIES = "/config/logging.properties";
    private static final String MSG_NOT_FOUND = "{0} not found";

    static {
        InputStream stream = Log.class.getResourceAsStream(LOGGING_PROPERTIES);
        if (stream == null) {
            Logger.getGlobal().warning(MessageFormat.format(MSG_NOT_FOUND, LOGGING_PROPERTIES));
        }
        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Log() {
    }

    public static void debug(Exception exception, String pattern, Object... arguments) {
        _log(Level.FINE, exception, pattern, arguments);
    }

    private static void _log(Level level, Exception exception, String pattern, Object... arguments) {
        StackTraceElement ste = findCaller();
        if (ste != null) {
            String sourceClass = ste.getClassName();
            Logger logger = Logger.getLogger(sourceClass);
            if (logger.isLoggable(level)) {
                String sourceMethod = ste.getMethodName();
                ResourceBundle
                        bundle =
                        ResourceBundle.getBundle(LOCATION_ERRORS, Locale.getDefault(),
                                Log.class.getClassLoader());
                if (exception == null) {
                    logger.logrb(level, sourceClass, sourceMethod, bundle, pattern, arguments);
                } else {
                    logger.logrb(level, sourceClass, sourceMethod, bundle, pattern, arguments);
                    logger.logrb(level, sourceClass, sourceMethod, bundle, pattern, exception);
                }
            }
        }
    }

    private static StackTraceElement findCaller() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int length = trace.length;
        for (int i = 1; i < length; i++) {
            StackTraceElement ste = trace[i];
            if (!Objects.equals(Log.class.getName(), ste.getClassName())) {
                return ste;
            }
        }
        return null;
    }

    public static void debug(String pattern, Object... arguments) {
        _log(Level.FINE, null, pattern, arguments);
    }

    public static void error(Exception exception, String pattern, Object... arguments) {
        _log(Level.SEVERE, exception, pattern, arguments);
    }

    public static void error(String pattern, Object... arguments) {
        _log(Level.SEVERE, null, pattern, arguments);
    }

    public static void info(Exception exception, String pattern, Object... arguments) {
        _log(Level.INFO, exception, pattern, arguments);
    }

    public static void info(String pattern, Object... arguments) {
        _log(Level.INFO, null, pattern, arguments);
    }

    public static void log(Level level, Exception exception, String pattern, Object... arguments) {
        _log(level, exception, pattern, arguments);
    }

    public static void log(Level level, String pattern, Object... arguments) {
        _log(level, null, pattern, arguments);
    }

    public static void warn(Exception exception, String pattern, Object... arguments) {
        _log(Level.WARNING, exception, pattern, arguments);
    }

    public static void warn(String pattern, Object... arguments) {
        _log(Level.WARNING, null, pattern, arguments);
    }
}
