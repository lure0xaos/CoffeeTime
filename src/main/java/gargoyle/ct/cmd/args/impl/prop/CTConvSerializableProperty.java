package gargoyle.ct.cmd.args.impl.prop;

import gargoyle.ct.convert.impl.SerializableConverter;

import java.io.Serializable;

public class CTConvSerializableProperty<T extends Serializable> extends CTConvProperty<T> {

    public CTConvSerializableProperty(String name, String value) {
        super(new SerializableConverter<>(), name, value);
    }
}
