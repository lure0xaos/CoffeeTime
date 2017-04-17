package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.pref.impl.convert.impl.SerializableConverter;

import java.io.Serializable;

public class CTPrefSerializableProperty<T extends Serializable> extends CTPrefProperty<T> {

    public CTPrefSerializableProperty(CTPreferencesProvider provider, String name) {
        this(provider, name, null);
    }

    public CTPrefSerializableProperty(CTPreferencesProvider provider, String name, T def) {
        super(new SerializableConverter<>(), provider, name, def);
    }
}
