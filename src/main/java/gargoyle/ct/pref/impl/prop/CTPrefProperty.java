package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.Converter;
import gargoyle.ct.log.Log;
import gargoyle.ct.prop.impl.CTBaseObservableProperty;

import java.util.Objects;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class CTPrefProperty<T> extends CTBaseObservableProperty<T> {
    private final Preferences prefs;

    protected CTPrefProperty(Converter<T> converter, Preferences preferences, String name) {
        this(converter, preferences, name, null);
    }

    public CTPrefProperty(Converter<T> converter, Preferences preferences, String name, T def) {
        super(converter, name, def);
        prefs = preferences;
    }

    @Override
    public T get(T def) {
        String value = prefs.get(name, null);
        return value == null ? def : converter.parse(value);
    }

    @Override
    public final T get() {
        String value = prefs.get(name, null);
        return value == null ? def : converter.parse(value);
    }

    @Override
    public final void set(T value) {
        T oldValue = get();
        if (Objects.equals(oldValue, value)) {
            return;
        }
        prefs.put(name, value == null ? null : converter.format(value));
        sync();
        firePropertyChange(value, oldValue);
    }

    private void sync() {
        try {
            prefs.flush();
        } catch (BackingStoreException ex) {
            Log.error(ex, ex.getMessage());
        }
    }
}
