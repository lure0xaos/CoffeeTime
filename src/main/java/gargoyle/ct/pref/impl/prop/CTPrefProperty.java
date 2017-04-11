package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.Converter;
import gargoyle.ct.log.Log;
import gargoyle.ct.pref.PropertyChangeEvent;
import gargoyle.ct.pref.PropertyChangeListener;
import gargoyle.ct.prop.CTObservableProperty;
import gargoyle.ct.prop.impl.CTBaseProperty;
import gargoyle.ct.prop.impl.PropertyChangeManager;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class CTPrefProperty<T> extends CTBaseProperty<T> implements CTObservableProperty<T> {
    private final Preferences prefs;

    protected CTPrefProperty(Converter<T> converter, Preferences preferences, String name) {
        this(converter, preferences, name, null);
    }

    public CTPrefProperty(Converter<T> converter, Preferences preferences, String name, T def) {
        super(converter, name, def);
        prefs = preferences;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener<T> listener) {
        PropertyChangeManager.getInstance().addPropertyChangeListener(this, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener<T> listener) {
        PropertyChangeManager.getInstance().removePropertyChangeListener(this, listener);
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
        prefs.put(name, value == null ? null : converter.format(value));
        sync();
        PropertyChangeManager.getInstance().firePropertyChange(this, new PropertyChangeEvent<>(this, name, oldValue, value));
    }

    private void sync() {
        try {
            prefs.flush();
        } catch (BackingStoreException e) {
            Log.error(e, e.getMessage());
        }
    }
}
