package gargoyle.ct.pref;

import gargoyle.ct.pref.impl.prop.CTPrefProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface CTPreferencesManager {
    void addPropertyChangeListener(CTPropertyChangeListener listener);

    @NotNull <T> CTPrefProperty<T> getProperty(String name);

    @NotNull <E extends Enum<E>> CTPrefProperty<E> getProperty(Class<E> type);

    @NotNull Set<String> getPropertyNames();

    boolean hasProperty(String name);

    void removePropertyChangeListener(CTPropertyChangeListener listener);
}
