package gargoyle.ct.prop.impl.simple;

public class CTSimpleBooleanProperty extends CTSimpleProperty<Boolean> {
    public CTSimpleBooleanProperty(String name) {
        this(name, false);
    }

    public CTSimpleBooleanProperty(String name, boolean def) {
        super(name, def);
    }
}
