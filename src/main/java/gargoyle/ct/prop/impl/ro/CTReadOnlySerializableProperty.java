package gargoyle.ct.prop.impl.ro;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class CTReadOnlySerializableProperty<T extends Serializable> extends CTBaseReadOnlyProperty<T> {
    public CTReadOnlySerializableProperty(String name, @NotNull T value) {
        super(name, value);
    }
}
