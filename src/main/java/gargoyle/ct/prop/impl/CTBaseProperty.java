package gargoyle.ct.prop.impl;

import gargoyle.ct.prop.CTProperty;

import java.text.MessageFormat;

public abstract class CTBaseProperty<T> implements CTProperty<T> {

    protected final String name;

    protected CTBaseProperty(String name) {
        this.name = name;
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
        return MessageFormat.format("CTBaseProperty'{'name=''{0}'''}'", name);
    }
}
