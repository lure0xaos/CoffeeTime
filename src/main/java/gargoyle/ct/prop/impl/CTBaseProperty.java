package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.Converter;
import gargoyle.ct.prop.CTProperty;

public abstract class CTBaseProperty<T> implements CTProperty<T> {
    protected final Converter<T> converter;
    protected final T def;
    protected final String name;

    protected CTBaseProperty(Converter<T> converter, String name) {
        this.name = name;
        this.converter = converter;
        def = null;
    }

    public CTBaseProperty(Converter<T> converter, String name, T def) {
        this.name = name;
        this.converter = converter;
        this.def = def;
    }

    public T get(T def) {
        T value = get();
        return value == null ? def : value;
    }
}
