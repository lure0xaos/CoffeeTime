package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.log.Log;
import gargoyle.ct.pref.impl.convert.Converter;
import gargoyle.ct.prop.impl.CTBaseObservableProperty;

import java.util.Objects;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class CTPrefProperty<T> extends CTBaseObservableProperty<T> {

    protected final String       def;
    protected final Converter<T> converter;
    private final   Preferences  preferences;

    protected CTPrefProperty(Converter<T> converter, Preferences preferences, String name) {
        this(converter, preferences, name, null);
    }

    public CTPrefProperty(Converter<T> converter, Preferences preferences, String name, T def) {
        super(name);
        this.def = converter.format(def);
        this.converter = converter;
        this.preferences = preferences;
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

    @Override
    public final T get() {
        return get(converter.parse(def));
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

    private void sync() {
        try {
            preferences.flush();
        } catch (BackingStoreException ex) {
            Log.error(ex, ex.getMessage());
        }
    }
}
