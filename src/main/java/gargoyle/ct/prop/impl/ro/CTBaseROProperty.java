package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTROProperty;

import java.text.MessageFormat;

public abstract class CTBaseROProperty<T> implements CTROProperty<T> {

    private final String   name;
    private final T        value;
    private final Class<T> type;

    protected CTBaseROProperty(String name, T value) {
        type = (Class<T>) value.getClass();
        this.name = name;
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public Class<T> type() {
        return type;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return MessageFormat.format("CTBaseROProperty'{'name=''{0}'', value={1}'}'", name, value);
    }
}
