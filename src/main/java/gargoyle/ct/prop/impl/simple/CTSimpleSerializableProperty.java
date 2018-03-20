package gargoyle.ct.prop.impl.simple;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class CTSimpleSerializableProperty<T extends Serializable> extends CTSimpleProperty<T> {
    public CTSimpleSerializableProperty(Class<T> type, String name) {
        super(type, name);
    }

    public CTSimpleSerializableProperty(Class<T> type, String name, @NotNull T def) {
        super(name, def);
    }
}
