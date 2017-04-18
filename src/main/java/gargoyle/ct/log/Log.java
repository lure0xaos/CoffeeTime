package gargoyle.ct.log;

import gargoyle.ct.log.LogHelper.LEVEL;
import gargoyle.ct.messages.util.UTF8Control;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public final class Log {

    private static final String LOGGING_PROPERTIES = "/config/logging.properties";
    private static final String LOCATION_ERRORS    = "messages/errors";

    static {
        LogHelper.config(LOGGING_PROPERTIES);
    }

    private Log() {
    }

    public static void debug(Throwable exception, String pattern, Object... arguments) {
        _log(LEVEL.DEBUG, exception, pattern, arguments);
    }

    @SuppressWarnings("OverlyStrongTypeCast")
    private static void _log(LEVEL level, Throwable exception, String pattern, Object... arguments) {
        StackTraceElement ste = findCaller();
        if (ste != null) {
            String source = ste.getClassName();
            if (LogHelper.isLoggable(source, level)) {
                String method = ste.getMethodName();
                if (pattern != null) {
                    LogHelper.log(source, level, source, method, getResourceBundle(), pattern, arguments);
                    if (exception != null) {
                        LogHelper.log(source, level, source, method, exception.getMessage(), exception);
                    }
                    return;
                }
                if (exception != null) {
                    LogHelper.log(source, level, source, method, null, exception.getLocalizedMessage(), null);
                    LogHelper.log(source, level, source, method, exception.getMessage(), exception);
                    return;
                }
                LogHelper.log(source, level, source, method, null, "", null);
            }
        }
    }

    private static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle(LOCATION_ERRORS, Locale.getDefault(), UTF8Control.getControl());
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
        _log(LEVEL.DEBUG, null, pattern, arguments);
    }

    public static void error(Throwable exception, String pattern, Object... arguments) {
        _log(LEVEL.ERROR, exception, pattern, arguments);
    }

    public static void error(String pattern, Object... arguments) {
        _log(LEVEL.ERROR, null, pattern, arguments);
    }

    public static void info(Throwable exception, String pattern, Object... arguments) {
        _log(LEVEL.INFO, exception, pattern, arguments);
    }

    public static void info(String pattern, Object... arguments) {
        _log(LEVEL.INFO, null, pattern, arguments);
    }

    public static void log(LEVEL level, Throwable exception, String pattern, Object... arguments) {
        _log(level, exception, pattern, arguments);
    }

    public static void log(LEVEL level, String pattern, Object... arguments) {
        _log(level, null, pattern, arguments);
    }

    public static void warn(Throwable exception, String pattern, Object... arguments) {
        _log(LEVEL.WARNING, exception, pattern, arguments);
    }

    public static void warn(String pattern, Object... arguments) {
        _log(LEVEL.WARNING, null, pattern, arguments);
    }
}
