package gargoyle.ct.prop.impl.simple;

import org.jetbrains.annotations.NotNull;

public class CTSimpleBytesProperty extends CTSimpleProperty<byte[]> {
    public CTSimpleBytesProperty(String name) {
        this(name, new byte[0]);
    }

    public CTSimpleBytesProperty(String name, @NotNull byte[] def) {
        super(name, def);
    }
}
