package gargoyle.ct.prop.impl.ro;

import org.jetbrains.annotations.NotNull;

public class CTReadOnlyBytesProperty extends CTBaseReadOnlyProperty<byte[]> {
    public CTReadOnlyBytesProperty(String name, @NotNull byte[] value) {
        super(name, value);
    }
}
