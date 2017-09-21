package gargoyle.ct.pref;

import gargoyle.ct.pref.impl.prop.CTPrefProperty;

import java.util.Set;

public interface CTPreferencesManager {
    void addPropertyChangeListener(CTPropertyChangeListener listener);

    <T> CTPrefProperty<T> getProperty(String name);

    <E extends Enum<E>> CTPrefProperty<E> getProperty(Class<E> type);

    Set<String> getPropertyNames();

    boolean hasProperty(String name);

    void removePropertyChangeListener(CTPropertyChangeListener listener);
}
