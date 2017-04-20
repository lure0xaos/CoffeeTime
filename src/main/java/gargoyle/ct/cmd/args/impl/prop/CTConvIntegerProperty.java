package gargoyle.ct.cmd.args.impl.prop;

import gargoyle.ct.prop.CTRONumberProperty;

public class CTConvIntegerProperty extends CTConvProperty<Integer> implements CTRONumberProperty<Integer> {

    public CTConvIntegerProperty(String name, String value) {
        super(Integer.class, name, value);
    }
}
