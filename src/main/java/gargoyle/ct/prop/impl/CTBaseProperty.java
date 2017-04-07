package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.Converter;
import gargoyle.ct.prop.CTProperty;

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
