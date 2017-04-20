package gargoyle.ct.prop.impl;

import gargoyle.ct.prop.CTProperty;

import java.text.MessageFormat;

public abstract class CTBaseProperty<T> implements CTProperty<T> {

    protected final String   name;
    private final   Class<T> type;

    protected CTBaseProperty(Class<T> type, String name) {
        this.type = type;
        this.name = name;
    }

    public T get(T def) {
        T value = get();
        return value == null ? def : value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Class<T> type() {
        return type;
    }

    @Override
    public String toString() {
        return MessageFormat.format("CTBaseProperty'{'name=''{0}'''}'", name);
    }
}
