package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.impl.SerializableConverter;

import java.io.Serializable;

public class CTSimpleSerializableProperty<T extends Serializable> extends CTSimpleProperty<T> {
    public CTSimpleSerializableProperty(String name) {
        this(name, null);
    }

    public CTSimpleSerializableProperty(String name, T def) {
        super(new SerializableConverter<>(), name, def);
    }
}
