package gargoyle.ct.cmd.args.impl.prop;

import gargoyle.ct.convert.impl.FloatConverter;
import gargoyle.ct.prop.CTRONumberProperty;

public class CTConvFloatProperty extends CTConvProperty<Float> implements CTRONumberProperty<Float> {

    public CTConvFloatProperty(String name, String value) {
        super(new FloatConverter(), name, value);
    }
}
