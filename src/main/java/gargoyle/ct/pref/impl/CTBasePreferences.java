package gargoyle.ct.pref.impl;

import gargoyle.ct.log.Log;
import gargoyle.ct.pref.CTPreferencesManager;
import gargoyle.ct.pref.CTPropertyChangeListener;
import gargoyle.ct.pref.impl.prop.CTPrefProperty;
import gargoyle.ct.prop.impl.CTPropertyChangeManager;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

abstract class CTBasePreferences implements CTPreferencesManager {

    private static final String MSG_JAVA_UTIL_LOGGING_ERROR = "java.util.logging error";

    static {
        try {
            Class<?> loggerClass = Class.forName("sun.util.logging.PlatformLogger");
            Class<?> levelClass  = Class.forName("sun.util.logging.PlatformLogger$Level");
            //noinspection unchecked
            loggerClass.getMethod("setLevel", levelClass)
                       .invoke(loggerClass.getMethod("getLogger", String.class).invoke(null, "java.util.preferences"),
                               //NON-NLS
                               Enum.valueOf((Class<Enum>) levelClass, "OFF")); //NON-NLS
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException
                ex) {
            Log.warn(ex, MSG_JAVA_UTIL_LOGGING_ERROR);
        }
    }

    protected final Preferences preferences;
    private final Map<String, CTPrefProperty<?>> properties = new HashMap<>();

    protected CTBasePreferences(Class<?> clazz) {
        preferences = Preferences.userNodeForPackage(clazz);
    }

    @Override
    public void addPropertyChangeListener(CTPropertyChangeListener listener) {
        for (CTPrefProperty<?> property : properties.values()) {
            CTPropertyChangeManager.getInstance().addPropertyChangeListener(property, listener);
        }
    }

    @Override
    public void removePropertyChangeListener(CTPropertyChangeListener listener) {
        for (CTPrefProperty<?> property : properties.values()) {
            CTPropertyChangeManager.getInstance().removePropertyChangeListener(property, listener);
        }
    }

    protected <T> void addProperty(CTPrefProperty<T> property) {
        properties.put(property.name(), property);
    }

    @SuppressWarnings("unchecked")
    protected <T> CTPrefProperty<T> getProperty(String name) {
        return (CTPrefProperty<T>) properties.get(name);
    }

    @SuppressWarnings("unchecked")
    protected <E extends Enum<E>> CTPrefProperty<E> getProperty(Class<E> type) {
        return (CTPrefProperty<E>) properties.get(type.getSimpleName());
    }
}
