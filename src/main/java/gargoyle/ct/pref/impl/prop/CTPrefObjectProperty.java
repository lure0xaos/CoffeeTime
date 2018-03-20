package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.Converter;
import gargoyle.ct.pref.CTPreferencesProvider;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class CTPrefObjectProperty<T extends Serializable> extends CTPrefProperty<T> {
    public CTPrefObjectProperty(Class<T> type, @NotNull CTPreferencesProvider provider, String name, @NotNull Converter<T> converter) {
        this(type, provider, name, null, converter);
    }

    public CTPrefObjectProperty(Class<T> type, @NotNull CTPreferencesProvider provider, String name, T def, @NotNull Converter<T> converter) {
        super(type, converter, provider, name, def);
    }
}
