package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.impl.ByteConverter;

public class CTSimpleByteProperty extends CTSimpleProperty<Byte> {
    public CTSimpleByteProperty(String name) {
        this(name, (byte) 0);
    }

    public CTSimpleByteProperty(String name, Byte def) {
        super(new ByteConverter(), name, def);
    }
}