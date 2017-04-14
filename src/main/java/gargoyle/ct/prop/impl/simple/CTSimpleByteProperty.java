package gargoyle.ct.prop.impl.simple;

import gargoyle.ct.prop.CTNumberProperty;

public class CTSimpleByteProperty extends CTSimpleProperty<Byte> implements CTNumberProperty<Byte> {

    public CTSimpleByteProperty(String name) {
        this(name, (byte) 0);
    }

    public CTSimpleByteProperty(String name, Byte def) {
        super(name, def);
    }
}
