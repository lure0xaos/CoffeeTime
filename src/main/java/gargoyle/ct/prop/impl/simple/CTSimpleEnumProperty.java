package gargoyle.ct.prop.impl.simple;

import org.jetbrains.annotations.NotNull;

public class CTSimpleEnumProperty<E extends Enum<E>> extends CTSimpleProperty<E> {
    public CTSimpleEnumProperty(Class<E> type, String name) {
        super(type, name);
    }

    public CTSimpleEnumProperty(Class<E> type, String name, @NotNull E def) {
        super(name, def);
    }

    public CTSimpleEnumProperty(@NotNull Class<E> type) {
        super(type, type.getSimpleName());
    }

    public CTSimpleEnumProperty(Class<E> type, @NotNull E def) {
        super(type.getSimpleName(), def);
    }

    public CTSimpleEnumProperty(String name, @NotNull E def) {
        super(name, def);
    }

    public CTSimpleEnumProperty(@NotNull E def) {
        super(def.getClass().getSimpleName(), def);
    }
}
