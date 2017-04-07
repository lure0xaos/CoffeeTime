package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.Converter;
import gargoyle.ct.log.Log;
import gargoyle.ct.prop.impl.CTBaseProperty;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class CTPrefProperty<T> extends CTBaseProperty<T> {
    private final Preferences prefs;

    protected CTPrefProperty(Converter<T> converter, Preferences preferences, String name) {
        super(converter, name);
        prefs = preferences;
    }

    public CTPrefProperty(Converter<T> converter, Preferences preferences, String name, T def) {
        super(converter, name);
        prefs = preferences;
        set(def);
    }

    @Override
    public final T get(T def) {
        String value = prefs.get(name, null);
        return value == null ? def : converter.parse(value);
    }

    @Override
    public final void set(T value) {
        prefs.put(name, value == null ? null : converter.format(value));
        sync();
    }

    private void sync() {
        try {
            prefs.flush();
        } catch (BackingStoreException e) {
            Log.error(e, e.getMessage());
        }
    }
}
