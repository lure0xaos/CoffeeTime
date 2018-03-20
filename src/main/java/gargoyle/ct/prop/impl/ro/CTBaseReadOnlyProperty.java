package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTReadOnlyProperty;
import gargoyle.ct.util.Defend;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public abstract class CTBaseReadOnlyProperty<T> implements CTReadOnlyProperty<T> {
    private final String name;
    @NotNull
    private final Class<T> type;
    @NotNull
    private final T value;

    @SuppressWarnings("unchecked")
    protected CTBaseReadOnlyProperty(String name, @NotNull T value) {
        Defend.notNull(value, "ReadOnly value cannot be null");
        type = (Class<T>) value.getClass();
        this.name = name;
        this.value = value;
    }

    @NotNull
    @Override
    public T get() {
        return value;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean isPresent() {
        return value != null;
    }

    @Override
    public String name() {
        return name;
    }

    @NotNull
    @Override
    public Class<T> type() {
        return type;
    }

    @NotNull
    @Override
    public String toString() {
        return MessageFormat.format("CTBaseReadOnlyProperty'{'name=''{0}'', value={1}'}'", name, value);
    }
}
