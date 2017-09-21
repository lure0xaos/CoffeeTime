package gargoyle.ct.prop.impl.simple;

import java.io.Serializable;

public class CTSimpleSerializableProperty<T extends Serializable> extends CTSimpleProperty<T> {
    public CTSimpleSerializableProperty(Class<T> type, String name) {
        super(type, name);
    }

    public CTSimpleSerializableProperty(Class<T> type, String name, T def) {
        super(name, def);
    }
}
