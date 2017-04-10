package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.impl.EnumConverter;

public class CTSimpleEnumProperty<E extends Enum<E>> extends CTSimpleProperty<E> {
    public CTSimpleEnumProperty(Class<E> type, String name) {
        this(type, name, null);
    }

    public CTSimpleEnumProperty(Class<E> type, String name, E def) {
        super(new EnumConverter<>(type), name, def);
    }

    public CTSimpleEnumProperty(Class<E> type) {
        this(type, type.getSimpleName(), null);
    }

    public CTSimpleEnumProperty(Class<E> type, E def) {
        super(new EnumConverter<>(type), type.getSimpleName(), def);
    }

    public CTSimpleEnumProperty(String name, E def) {
        super(new EnumConverter<>((Class<E>) def.getClass()), name, def);
    }

    public CTSimpleEnumProperty(E def) {
        super(new EnumConverter<>((Class<E>) def.getClass()), def.getClass().getSimpleName(), def);
    }
}
