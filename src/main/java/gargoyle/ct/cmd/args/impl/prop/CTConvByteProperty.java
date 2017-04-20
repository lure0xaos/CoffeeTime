package gargoyle.ct.cmd.args.impl.prop;

import gargoyle.ct.prop.CTRONumberProperty;

public class CTConvByteProperty extends CTConvProperty<Byte> implements CTRONumberProperty<Byte> {

    public CTConvByteProperty(String name, String value) {
        super(Byte.class, name, value);
    }
}
