package gargoyle.ct.cmd.args.impl.prop;

import gargoyle.ct.convert.impl.LongConverter;
import gargoyle.ct.prop.CTRONumberProperty;

public class CTConvLongProperty extends CTConvProperty<Long> implements CTRONumberProperty<Long> {

    public CTConvLongProperty(String name, String value) {
        super(new LongConverter(), name, value);
    }
}
