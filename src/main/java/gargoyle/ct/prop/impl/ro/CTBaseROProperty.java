package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTROProperty;

import java.text.MessageFormat;

public abstract class CTBaseROProperty<T> implements CTROProperty<T> {

    protected final String name;
    private final   T      value;

    protected CTBaseROProperty(String name, T value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return MessageFormat.format("CTBaseROProperty'{'name=''{0}'', value={1}'}'", name, value);
    }
}
