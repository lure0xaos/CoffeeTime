package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.impl.SerializableConverter;
import gargoyle.ct.pref.CTPreferencesProvider;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class CTPrefSerializableProperty<T extends Serializable> extends CTPrefProperty<T> {
    public CTPrefSerializableProperty(Class<T> type, @NotNull CTPreferencesProvider provider, String name) {
        this(type, provider, name, null);
    }

    public CTPrefSerializableProperty(Class<T> type, @NotNull CTPreferencesProvider provider, String name, T def) {
        super(type, new SerializableConverter<>(), provider, name, def);
    }
}
