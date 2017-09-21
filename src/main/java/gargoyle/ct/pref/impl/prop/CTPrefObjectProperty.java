package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.Converter;
import gargoyle.ct.pref.CTPreferencesProvider;

import java.io.Serializable;

public class CTPrefObjectProperty<T extends Serializable> extends CTPrefProperty<T> {
    public CTPrefObjectProperty(Class<T> type, CTPreferencesProvider provider, String name, Converter<T> converter) {
        this(type, provider, name, null, converter);
    }

    public CTPrefObjectProperty(Class<T> type, CTPreferencesProvider provider, String name, T def, Converter<T> converter) {
        super(type, converter, provider, name, def);
    }
}
