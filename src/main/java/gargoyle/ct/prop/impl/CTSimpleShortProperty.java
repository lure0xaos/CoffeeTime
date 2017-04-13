package gargoyle.ct.prop.impl;

public class CTSimpleShortProperty extends CTSimpleProperty<Short> {
    public CTSimpleShortProperty(String name) {
        this(name, (short) 0);
    }

    public CTSimpleShortProperty(String name, Short def) {
        super(name, def);
    }
}
