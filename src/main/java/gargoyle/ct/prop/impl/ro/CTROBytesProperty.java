package gargoyle.ct.prop.impl.ro;

public class CTROBytesProperty extends CTBaseROProperty<byte[]> {

    public CTROBytesProperty(String name) {
        this(name, new byte[0]);
    }

    public CTROBytesProperty(String name, byte[] value) {
        super(name, value);
    }
}
