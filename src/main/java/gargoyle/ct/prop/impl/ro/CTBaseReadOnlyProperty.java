package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTReadOnlyProperty;
import gargoyle.ct.util.Defend;

import java.text.MessageFormat;

public abstract class CTBaseReadOnlyProperty<T> implements CTReadOnlyProperty<T> {
    private final String name;
    private final Class<T> type;
    private final T value;

    @SuppressWarnings("unchecked")
    protected CTBaseReadOnlyProperty(String name, T value) {
        Defend.notNull(value, "ReadOnly value cannot be null");
        type = (Class<T>) value.getClass();
        this.name = name;
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public boolean isPresent() {
        return value != null;
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
        return MessageFormat.format("CTBaseReadOnlyProperty'{'name=''{0}'', value={1}'}'", name, value);
    }
}
