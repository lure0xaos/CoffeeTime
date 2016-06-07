package gargoyle.ct;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Log {
	private static void _log(final Level level, final Exception exception, final String pattern,
			final StackTraceElement ste, final Object... arguments) {
		final String sourceClass = ste.getClassName();
		final Logger logger = Logger.getLogger(sourceClass);
		if (logger.isLoggable(level)) {
			final String sourceMethod = ste.getMethodName();
			final ResourceBundle bundle = ResourceBundle.getBundle("errors", Locale.getDefault(),
					Thread.currentThread().getContextClassLoader());
			logger.logrb(level, sourceClass, sourceMethod, bundle, pattern, arguments, exception);
		}
	}

	public static void debug(final Exception exception, final String pattern, final Object... arguments) {
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		Log._log(Level.FINE, exception, pattern, ste, arguments);
	}

	public static void debug(final String pattern, final Object... arguments) {
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		Log._log(Level.FINE, null, pattern, ste, arguments);
	}

	public static void error(final Exception exception, final String pattern, final Object... arguments) {
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		Log._log(Level.SEVERE, exception, pattern, ste, arguments);
	}

	public static void error(final String pattern, final Object... arguments) {
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		Log._log(Level.SEVERE, null, pattern, ste, arguments);
	}

	public static void info(final Exception exception, final String pattern, final Object... arguments) {
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		Log._log(Level.INFO, exception, pattern, ste, arguments);
	}

	public static void info(final String pattern, final Object... arguments) {
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		Log._log(Level.INFO, null, pattern, ste, arguments);
	}

	public static void log(final Level level, final Exception exception, final String pattern,
			final Object... arguments) {
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		Log._log(level, exception, pattern, ste, arguments);
	}

	public static void log(final Level level, final String pattern, final Object... arguments) {
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		Log._log(level, null, pattern, ste, arguments);
	}

	public static void warn(final Exception exception, final String pattern, final Object... arguments) {
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		Log._log(Level.WARNING, exception, pattern, ste, arguments);
	}

	public static void warn(final String pattern, final Object... arguments) {
		final StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		Log._log(Level.WARNING, null, pattern, ste, arguments);
	}

	private Log() {
		super();
	}
}
