package gargoyle.ct.prop.impl.ro;

public class CTReadOnlyEnumProperty<E extends Enum<E>> extends CTBaseReadOnlyProperty<E> {
    public CTReadOnlyEnumProperty(Class<E> type, E value) {
        super(type.getSimpleName(), value);
    }

    public CTReadOnlyEnumProperty(String name, E value) {
        super(name, value);
    }

    public CTReadOnlyEnumProperty(E value) {
        super(value.getClass().getSimpleName(), value);
    }
}
