package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTReadOnlyNumberProperty;
import org.jetbrains.annotations.NotNull;

public class CTReadOnlyByteProperty extends CTBaseReadOnlyProperty<Byte> implements CTReadOnlyNumberProperty<Byte> {
    public CTReadOnlyByteProperty(String name, @NotNull Byte value) {
        super(name, value);
    }
}
