package gargoyle.ct.prop.impl.ro;

import java.io.Serializable;

public class CTReadOnlySerializableProperty<T extends Serializable> extends CTBaseReadOnlyProperty<T> {
    public CTReadOnlySerializableProperty(String name, T value) {
        super(name, value);
    }
}
