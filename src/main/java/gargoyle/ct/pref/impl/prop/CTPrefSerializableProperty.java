package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.convert.impl.SerializableConverter;

import java.io.Serializable;
import java.util.prefs.Preferences;

public class CTPrefSerializableProperty<T extends Serializable> extends CTPrefProperty<T> {
    public CTPrefSerializableProperty(Preferences preferences, String name) {
        this(preferences, name, null);
    }

    public CTPrefSerializableProperty(Preferences preferences, String name, T def) {
        super(new SerializableConverter<>(), preferences, name, def);
    }
}
