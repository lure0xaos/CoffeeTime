package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.Converter;

import java.text.MessageFormat;
import java.util.Objects;

public abstract class CTSimpleProperty<T> extends CTBaseObservableProperty<T> {
    private T value;

    protected CTSimpleProperty(Converter<T> converter, String name) {
        super(converter, name);
    }

    public CTSimpleProperty(Converter<T> converter, String name, T def) {
        super(converter, name, def);
        value = def;
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
        T oldValue = get();
        if (Objects.equals(oldValue, value)) {
            return;
        }
        this.value = value;
        Thread thread = firePropertyChange(value, oldValue);
        try {
            if (thread != null) {
                thread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return MessageFormat.format("CTSimpleProperty'{'name=''{0}'', value={1}, def={2}'}'", name, value, def);
    }
}
