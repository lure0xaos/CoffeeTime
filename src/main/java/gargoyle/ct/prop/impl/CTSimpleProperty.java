package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.Converter;

public abstract class CTSimpleProperty<T> extends CTBaseProperty<T> {
    private T value;

    protected CTSimpleProperty(Converter<T> converter, String name) {
        super(converter, name);
    }

    public CTSimpleProperty(Converter<T> converter, String name, T def) {
        super(converter, name, def);
    }

    public T get(T def) {
        T value = get();
        return value == null ? def : value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T value) {
        this.value = value;
    }
}
