package gargoyle.ct.prop.impl.simple;

import gargoyle.ct.prop.CTNumberProperty;

public class CTSimpleIntegerProperty extends CTSimpleProperty<Integer> implements CTNumberProperty<Integer> {
    public CTSimpleIntegerProperty(String name) {
        this(name, 0);
    }

    public CTSimpleIntegerProperty(String name, int def) {
        super(name, def);
    }
}
