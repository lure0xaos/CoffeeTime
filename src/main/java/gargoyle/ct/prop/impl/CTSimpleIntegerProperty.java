package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.impl.IntegerConverter;

public class CTSimpleIntegerProperty extends CTSimpleProperty<Integer> {
    public CTSimpleIntegerProperty(String name) {
        this(name, 0);
    }

    public CTSimpleIntegerProperty(String name, int def) {
        super(new IntegerConverter(), name, def);
    }
}
