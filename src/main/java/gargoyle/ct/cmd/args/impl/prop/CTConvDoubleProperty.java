package gargoyle.ct.cmd.args.impl.prop;

import gargoyle.ct.convert.impl.DoubleConverter;
import gargoyle.ct.prop.CTRONumberProperty;

public class CTConvDoubleProperty extends CTConvProperty<Double> implements CTRONumberProperty<Double> {

    public CTConvDoubleProperty(String name, String value) {
        super(new DoubleConverter(), name, value);
    }
}
