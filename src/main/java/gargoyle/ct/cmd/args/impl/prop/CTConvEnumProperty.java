package gargoyle.ct.cmd.args.impl.prop;

import gargoyle.ct.convert.impl.EnumConverter;

public class CTConvEnumProperty<E extends Enum<E>> extends CTConvProperty<E> {

    public CTConvEnumProperty(Class<E> type, String name, String value) {
        super(new EnumConverter<>(type), name, value);
    }

    public CTConvEnumProperty(Class<E> type, String value) {
        super(new EnumConverter<>(type), type.getSimpleName(), value);
    }
}
