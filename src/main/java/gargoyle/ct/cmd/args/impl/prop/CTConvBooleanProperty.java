package gargoyle.ct.cmd.args.impl.prop;

import gargoyle.ct.convert.impl.BooleanConverter;

public class CTConvBooleanProperty extends CTConvProperty<Boolean> {

    public CTConvBooleanProperty(String name, String value) {
        super(new BooleanConverter(), name, value);
    }
}
