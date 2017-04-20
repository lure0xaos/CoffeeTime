package gargoyle.ct.prop.impl.ro;

public class CTROEnumProperty<E extends Enum<E>> extends CTBaseROProperty<E> {

    public CTROEnumProperty(Class<E> type, E value) {
        super(type.getSimpleName(), value);
    }

    public CTROEnumProperty(String name, E value) {
        super(name, value);
    }

    public CTROEnumProperty(E value) {
        super(value.getClass().getSimpleName(), value);
    }
}
