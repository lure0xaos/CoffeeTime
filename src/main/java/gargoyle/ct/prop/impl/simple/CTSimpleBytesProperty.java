package gargoyle.ct.prop.impl.simple;

public class CTSimpleBytesProperty extends CTSimpleProperty<byte[]> {

    public CTSimpleBytesProperty(String name) {
        this(name, new byte[0]);
    }

    public CTSimpleBytesProperty(String name, byte[] def) {
        super(name, def);
    }
}
