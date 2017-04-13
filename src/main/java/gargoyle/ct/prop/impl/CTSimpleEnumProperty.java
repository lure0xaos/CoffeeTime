package gargoyle.ct.prop.impl;

public class CTSimpleEnumProperty<E extends Enum<E>> extends CTSimpleProperty<E> {
    public CTSimpleEnumProperty(Class<E> type, String name) {
        this(type, name, null);
    }

    public CTSimpleEnumProperty(Class<E> type, String name, E def) {
        super(name, def);
    }

    public CTSimpleEnumProperty(Class<E> type) {
        this(type, type.getSimpleName(), null);
    }

    public CTSimpleEnumProperty(Class<E> type, E def) {
        super(type.getSimpleName(), def);
    }

    public CTSimpleEnumProperty(String name, E def) {
        super(name, def);
    }

    public CTSimpleEnumProperty(E def) {
        super(def.getClass().getSimpleName(), def);
    }
}
