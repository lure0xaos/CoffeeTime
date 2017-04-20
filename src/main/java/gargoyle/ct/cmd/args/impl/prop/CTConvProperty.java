package gargoyle.ct.cmd.args.impl.prop;

import gargoyle.ct.convert.Converter;
import gargoyle.ct.convert.Converters;
import gargoyle.ct.prop.impl.ro.CTBaseROProperty;

public class CTConvProperty<T> extends CTBaseROProperty<T> {

    protected CTConvProperty(Class<T> type, String name, String val) {
        this(type, Converters.get(type), name, val);
    }

    protected CTConvProperty(Class<T> type, Converter<T> converter, String name, String val) {
        super(name, converter.parse(val));
    }
}
