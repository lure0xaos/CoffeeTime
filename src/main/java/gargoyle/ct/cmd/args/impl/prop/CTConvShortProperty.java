package gargoyle.ct.cmd.args.impl.prop;

import gargoyle.ct.convert.impl.ShortConverter;
import gargoyle.ct.prop.CTRONumberProperty;

public class CTConvShortProperty extends CTConvProperty<Short> implements CTRONumberProperty<Short> {

    public CTConvShortProperty(String name, String value) {
        super(new ShortConverter(), name, value);
    }
}
