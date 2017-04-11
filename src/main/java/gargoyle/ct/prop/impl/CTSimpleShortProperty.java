package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.impl.ShortConverter;

public class CTSimpleShortProperty extends CTSimpleProperty<Short> {
    public CTSimpleShortProperty(String name) {
        this(name, (short) 0);
    }

    public CTSimpleShortProperty(String name, Short def) {
        super(new ShortConverter(), name, def);
    }
}