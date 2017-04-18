package gargoyle.ct.log;

import gargoyle.ct.messages.util.UTF8Control;

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

    private static final String LOCATION_ERRORS    = "messages/errors";
    private static final String LOGGING_PROPERTIES = "/config/logging.properties";
    private static final String MSG_NOT_FOUND      = "configuration {0} not found";

    static {
        try (InputStream stream = Log.class.getResourceAsStream(LOGGING_PROPERTIES)) {
            if (stream == null) {
                Logger.getGlobal().warning(MessageFormat.format(MSG_NOT_FOUND, LOGGING_PROPERTIES));
            } else {
                LogManager.getLogManager().readConfiguration(stream);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Log() {
    }

    public static void debug(Throwable exception, String pattern, Object... arguments) {
        _log(Level.FINE, exception, pattern, arguments);
    }

    @SuppressWarnings("OverlyStrongTypeCast")
    private static void _log(Level level, Throwable exception, String pattern, Object... arguments) {
        StackTraceElement ste = findCaller();
        if (ste != null) {
            String sourceClass = ste.getClassName();
            Logger logger      = Logger.getLogger(sourceClass);
            if (logger.isLoggable(level)) {
                String sourceMethod = ste.getMethodName();
                ResourceBundle bundle = ResourceBundle.getBundle(LOCATION_ERRORS,
                                                                 Locale.getDefault(),
                                                                 UTF8Control.getControl());
                if (pattern != null && exception == null) {
                    logger.logrb(level, sourceClass, sourceMethod, bundle, pattern, arguments);
                    return;
                }
                if (pattern != null && exception != null) {
                    logger.logrb(level, sourceClass, sourceMethod, bundle, pattern, arguments);
                    logger.logrb(level,
                                 sourceClass,
                                 sourceMethod,
                                 (ResourceBundle) null,
                                 exception.getMessage(),
                                 exception);
                    return;
                }
                if (pattern == null && exception == null) {
                    logger.logrb(level, sourceClass, sourceMethod, (ResourceBundle) null, "", (Object[]) null);
                    return;
                }
                if (pattern == null && exception != null) {
                    logger.logrb(level,
                                 sourceClass,
                                 sourceMethod,
                                 (ResourceBundle) null,
                                 exception.getLocalizedMessage(),
                                 (Object[]) null);
                    logger.logrb(level,
                                 sourceClass,
                                 sourceMethod,
                                 (ResourceBundle) null,
                                 exception.getMessage(),
                                 exception);
                    return;
                }
            }
        }
    }

    private static StackTraceElement findCaller() {
        StackTraceElement[] trace  = Thread.currentThread().getStackTrace();
        int                 length = trace.length;
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

    public static void error(Throwable exception, String pattern, Object... arguments) {
        _log(Level.SEVERE, exception, pattern, arguments);
    }

    public static void error(String pattern, Object... arguments) {
        _log(Level.SEVERE, null, pattern, arguments);
    }

    public static void info(Throwable exception, String pattern, Object... arguments) {
        _log(Level.INFO, exception, pattern, arguments);
    }

    public static void info(String pattern, Object... arguments) {
        _log(Level.INFO, null, pattern, arguments);
    }

    public static void log(Level level, Throwable exception, String pattern, Object... arguments) {
        _log(level, exception, pattern, arguments);
    }

    public static void log(Level level, String pattern, Object... arguments) {
        _log(level, null, pattern, arguments);
    }

    public static void warn(Throwable exception, String pattern, Object... arguments) {
        _log(Level.WARNING, exception, pattern, arguments);
    }

    public static void warn(String pattern, Object... arguments) {
        _log(Level.WARNING, null, pattern, arguments);
    }
}
