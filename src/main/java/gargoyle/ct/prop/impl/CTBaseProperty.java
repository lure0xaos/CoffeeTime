package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.Converter;
import gargoyle.ct.prop.CTProperty;

import java.text.MessageFormat;

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

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return MessageFormat.format("CTBaseProperty'{'name=''{0}'', def={1}'}'", name, def);
    }
}
