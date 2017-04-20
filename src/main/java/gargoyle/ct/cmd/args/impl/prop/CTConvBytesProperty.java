package gargoyle.ct.cmd.args.impl.prop;

public class CTConvBytesProperty extends CTConvProperty<byte[]> {

    public CTConvBytesProperty(String name, String value) {
        super(byte[].class, name, value);
    }
}
