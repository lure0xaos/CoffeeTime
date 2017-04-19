package gargoyle.ct.cmd.args.impl.prop;

import gargoyle.ct.convert.Converter;
import gargoyle.ct.prop.impl.ro.CTBaseROProperty;

public class CTConvProperty<T> extends CTBaseROProperty<T> {

    public CTConvProperty(Converter<T> converter, String name, String val) {
        super(name, converter.parse(val));
    }
}
