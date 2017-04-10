package gargoyle.ct.pref.impl;

import gargoyle.ct.log.Log;
import gargoyle.ct.pref.CTPreferencesManager;
import gargoyle.ct.pref.PropertyChangeListener;
import gargoyle.ct.pref.impl.prop.CTPrefProperty;
import gargoyle.ct.prop.impl.PropertyChangeManager;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

abstract class CTBasePreferences implements CTPreferencesManager {
    private static final String MSG_JAVA_UTIL_LOGGING_ERROR = "java.util.logging error";

    static {
        try {
            Class<?> loggerClass = Class.forName("sun.util.logging.PlatformLogger");
            Class<?> levelClass = Class.forName("sun.util.logging.PlatformLogger$Level");
            //noinspection unchecked
            loggerClass.getMethod("setLevel", levelClass)
                    .invoke(loggerClass.getMethod("getLogger", String.class)
                                    .invoke(null, "java.util.preferences"), //NON-NLS
                            Enum.valueOf((Class<Enum>) levelClass, "OFF")); //NON-NLS
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            Log.warn(e, MSG_JAVA_UTIL_LOGGING_ERROR);
        }
    }

    protected final Preferences preferences;
    private final Map<String, CTPrefProperty> properties = new HashMap<>();

    protected CTBasePreferences(Class<?> clazz) {
        preferences = Preferences.userNodeForPackage(clazz);
    }

    protected <T> void addProperty(CTPrefProperty<T> property) {
        properties.put(property.name(), property);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        for (CTPrefProperty<?> property : properties.values()) {
            PropertyChangeManager.getInstance().addPropertyChangeListener(property, listener);
        }
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        for (CTPrefProperty<?> property : properties.values()) {
            PropertyChangeManager.getInstance().removePropertyChangeListener(property, listener);
        }
    }

    protected <T> CTPrefProperty<T> getProperty(String name) {
        return (CTPrefProperty<T>) properties.get(name);
    }

    protected <E extends Enum<E>> CTPrefProperty<E> getProperty(Class<E> type) {
        return (CTPrefProperty<E>) properties.get(type.getSimpleName());
    }
}
