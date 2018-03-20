package gargoyle.ct.prop.impl.ro;

import org.jetbrains.annotations.NotNull;

public class CTReadOnlyEnumProperty<E extends Enum<E>> extends CTBaseReadOnlyProperty<E> {
    public CTReadOnlyEnumProperty(Class<E> type, @NotNull E value) {
        super(type.getSimpleName(), value);
    }

    public CTReadOnlyEnumProperty(String name, @NotNull E value) {
        super(name, value);
    }

    public CTReadOnlyEnumProperty(@NotNull E value) {
        super(value.getClass().getSimpleName(), value);
    }
}
