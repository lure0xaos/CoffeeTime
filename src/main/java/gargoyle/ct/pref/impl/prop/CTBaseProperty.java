package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.convert.Converter;
import gargoyle.ct.pref.prop.CTProperty;

public abstract class CTBaseProperty<T> implements CTProperty<T> {
    protected final Converter<T> converter;
    protected final String name;

    public CTBaseProperty(Converter<T> converter, String name) {
        this.name = name;
        this.converter = converter;
    }

    public CTBaseProperty(Converter<T> converter, String name, T def) {
        this.name = name;
        this.converter = converter;
        set(def);
    }

    public final T get() {
        return get(null);
    }
}
