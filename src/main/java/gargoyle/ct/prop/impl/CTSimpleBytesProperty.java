package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.impl.BytesConverter;

public class CTSimpleBytesProperty extends CTSimpleProperty<byte[]> {
    public CTSimpleBytesProperty(String name) {
        this(name, new byte[0]);
    }

    public CTSimpleBytesProperty(String name, byte[] def) {
        super(new BytesConverter(), name, def);
    }
}
