package gargoyle.ct.cmd.args.impl.prop;

import gargoyle.ct.prop.CTRONumberProperty;

public class CTConvFloatProperty extends CTConvProperty<Float> implements CTRONumberProperty<Float> {

    public CTConvFloatProperty(String name, String value) {
        super(Float.class, name, value);
    }
}
