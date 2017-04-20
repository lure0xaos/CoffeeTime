package gargoyle.ct.prop.impl.ro;

import java.io.Serializable;

public class CTROSerializableProperty<T extends Serializable> extends CTBaseROProperty<T> {

    public CTROSerializableProperty(String name, T value) {
        super(name, value);
    }
}
