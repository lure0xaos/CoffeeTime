package gargoyle.ct.prop.impl.simple;

public class CTSimpleByteProperty extends CTSimpleProperty<Byte> {
    public CTSimpleByteProperty(String name) {
        this(name, (byte) 0);
    }

    public CTSimpleByteProperty(String name, Byte def) {
        super(name, def);
    }
}
