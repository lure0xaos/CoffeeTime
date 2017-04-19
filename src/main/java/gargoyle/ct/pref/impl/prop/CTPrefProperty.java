package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.Converter;
import gargoyle.ct.log.Log;
import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.prop.impl.CTBaseObservableProperty;

import java.util.Objects;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class CTPrefProperty<T> extends CTBaseObservableProperty<T> {

    protected final String       def;
    protected final Converter<T> converter;
    private final   Preferences  preferences;

    public CTPrefProperty(Converter<T> converter, CTPreferencesProvider provider, String name, T def) {
        this((Class<T>) def.getClass(), converter, provider, name, def);
    }

    private CTPrefProperty(Class<T> type, Converter<T> converter, CTPreferencesProvider provider, String name, T def) {
        super(type, name);
        this.def = converter.format(def);
        this.converter = converter;
        preferences = provider.preferences();
    }

    protected CTPrefProperty(Class<T> type, Converter<T> converter, CTPreferencesProvider provider, String name) {
        this(type, converter, provider, name, null);
    }

    @Override
    public final void set(T value) {
        T oldValue = get();
        if (Objects.equals(oldValue, value)) {
            return;
        }
        preferences.put(name, value == null ? null : converter.format(value));
        sync();
        firePropertyChange(value, oldValue);
    }

    @Override
    public final T get() {
        return get(converter.parse(def));
    }

    @Override
    public T get(T def) {
        String value = preferences.get(name, null);
        if (value == null) {
            return def;
        }
        try {
            return converter.parse(value);
        } catch (IllegalArgumentException ex) {
            Log.warn(ex, ex.getMessage());
            return def;
        }
    }

    private void sync() {
        try {
            preferences.flush();
        } catch (BackingStoreException ex) {
            Log.error(ex, ex.getMessage());
        }
    }
}
