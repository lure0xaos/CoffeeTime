package gargoyle.ct.cmd.args.impl.prop;

import gargoyle.ct.convert.impl.BytesConverter;

public class CTConvBytesProperty extends CTConvProperty<byte[]> {

    public CTConvBytesProperty(String name, String value) {
        super(new BytesConverter(), name, value);
    }
}
