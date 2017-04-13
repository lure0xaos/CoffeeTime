package gargoyle.ct.prop.impl;

public class CTSimpleIntegerProperty extends CTSimpleProperty<Integer> {
    public CTSimpleIntegerProperty(String name) {
        this(name, 0);
    }

    public CTSimpleIntegerProperty(String name, int def) {
        super(name, def);
    }
}
