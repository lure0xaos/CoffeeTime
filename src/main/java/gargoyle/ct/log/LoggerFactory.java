package gargoyle.ct.log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoggerFactory {

    private static Map<String, Logger> cache = new HashMap<>();
    private static ILoggerFactory loggerFactory;

    public static Logger getLogger(String name) {
        return cache.computeIfAbsent(name, (logger) -> getLoggerFactory().getLogger(logger));
    }

    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    @NotNull
    private static ILoggerFactory getLoggerFactory() {
        synchronized (ILoggerFactory.class) {
            if (loggerFactory == null)
                try {
                    loggerFactory = LoggerFactoryConfig.getLoggerFactoryClass().getConstructor().newInstance();
                    loggerFactory.configure(LoggerFactoryConfig.getConfiguration());
                } catch (ReflectiveOperationException | IOException e) {
                    throw new RuntimeException(e);
                }
        }
        return loggerFactory;
    }
}
