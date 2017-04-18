package gargoyle.ct.log;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

final class LogHelper {

    private static final String MSG_NOT_FOUND = "configuration {0} not found";

    private LogHelper() {}

    static void config(String config) {
        try (InputStream stream = LogHelper.class.getResourceAsStream(config)) {
            if (stream == null) {
                Logger.getGlobal().warning(MessageFormat.format(MSG_NOT_FOUND, config));
            } else {
                LogManager.getLogManager().readConfiguration(stream);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    static boolean isLoggable(String logger, LEVEL level) {
        return getLogger(logger).isLoggable(getLevel(level));
    }

    private static Logger getLogger(String logger) {
        return Logger.getLogger(logger);
    }

    private static Level getLevel(LEVEL level) {
        return Level.parse(level.getLevel());
    }

    static void log(String logger, LEVEL level, String sourceClass, String sourceMethod, ResourceBundle bundle,
                    String message, Object[] arguments) {
        getLogger(logger).logrb(getLevel(level), sourceClass, sourceMethod, bundle, message, arguments);
    }

    static void log(String logger, LEVEL level, String sourceClass, String sourceMethod, String message,
                    Throwable exception) {
        getLogger(logger).logrb(getLevel(level), sourceClass, sourceMethod, (ResourceBundle) null, message, exception);
    }

    enum LEVEL {
        DEBUG("FINE"),
        ERROR("SEVERE"),
        INFO("INFO"),
        WARNING("WARNING");
        private final String level;

        LEVEL(String level) {
            this.level = level;
        }

        String getLevel() {
            return level;
        }
    }
}
