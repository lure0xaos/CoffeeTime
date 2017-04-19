package gargoyle.ct.pref;

import gargoyle.ct.pref.impl.prop.CTPrefProperty;

import java.util.Set;

public interface CTPreferencesManager {

    void addPropertyChangeListener(CTPropertyChangeListener listener);

    @SuppressWarnings("unchecked")
    <T> CTPrefProperty<T> getProperty(String name);

    @SuppressWarnings("unchecked")
    <E extends Enum<E>> CTPrefProperty<E> getProperty(Class<E> type);

    Set<String> getPropertyNames();

    boolean hasProperty(String name);

    void removePropertyChangeListener(CTPropertyChangeListener listener);
}
