package gargoyle.ct.cmd.args.impl.prop;

import gargoyle.ct.prop.CTRONumberProperty;

public class CTConvLongProperty extends CTConvProperty<Long> implements CTRONumberProperty<Long> {

    public CTConvLongProperty(String name, String value) {
        super(Long.class, name, value);
    }
}
