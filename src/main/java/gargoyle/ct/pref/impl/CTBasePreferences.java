package gargoyle.ct.pref.impl;

import gargoyle.ct.log.Log;
import gargoyle.ct.pref.CTPreferencesManager;
import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.pref.CTPropertyChangeListener;
import gargoyle.ct.pref.impl.prop.CTPrefProperty;
import gargoyle.ct.prop.impl.CTPropertyChangeManager;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.prefs.Preferences;

abstract class CTBasePreferences implements CTPreferencesManager, CTPreferencesProvider {
    private static final String MSG_JAVA_UTIL_LOGGING_ERROR = "java.util.logging error";

    static {
        try {
            Class<?> loggerClass = Class.forName("sun.util.logging.PlatformLogger");
            Class<?> levelClass = Class.forName("sun.util.logging.PlatformLogger$Level");
            //noinspection unchecked,HardCodedStringLiteral
            loggerClass.getMethod("setLevel", levelClass)
                    .invoke(loggerClass.getMethod("getLogger", String.class).invoke(null, "java.util.preferences"),
                            Enum.valueOf((Class<Enum>) levelClass, "OFF")); //NON-NLS
        } catch (@NotNull IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException
                ex) {
            Log.warn(ex, MSG_JAVA_UTIL_LOGGING_ERROR);
        }
    }

    private final Preferences preferences;
    private final Map<String, CTPrefProperty<?>> properties = new HashMap<>();

    protected CTBasePreferences(@NotNull Class<?> clazz) {
        preferences = Preferences.userNodeForPackage(clazz);
    }

    protected final <T> void addProperty(@NotNull CTPrefProperty<T> property) {
        properties.put(property.name(), property);
    }

    @Override
    public final void addPropertyChangeListener(CTPropertyChangeListener listener) {
        for (CTPrefProperty<?> property : properties.values()) {
            CTPropertyChangeManager.getInstance().addPropertyChangeListener(property, listener);
        }
    }

    @NotNull
    @SuppressWarnings("unchecked")
    @Override
    public final <T> CTPrefProperty<T> getProperty(String name) {
        return (CTPrefProperty<T>) properties.get(name);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    @Override
    public final <E extends Enum<E>> CTPrefProperty<E> getProperty(Class<E> type) {
        return (CTPrefProperty<E>) properties.get(type.getSimpleName());
    }

    @NotNull
    @Override
    public final Set<String> getPropertyNames() {
        return properties.keySet();
    }

    @Override
    public final boolean hasProperty(String name) {
        return properties.containsKey(name);
    }

    @Override
    public final void removePropertyChangeListener(CTPropertyChangeListener listener) {
        for (CTPrefProperty<?> property : properties.values()) {
            CTPropertyChangeManager.getInstance().removePropertyChangeListener(property, listener);
        }
    }

    @Override
    public final Preferences preferences() {
        return preferences;
    }
}
