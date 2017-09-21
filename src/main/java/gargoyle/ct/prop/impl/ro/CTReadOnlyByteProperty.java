package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTReadOnlyNumberProperty;

public class CTReadOnlyByteProperty extends CTBaseReadOnlyProperty<Byte> implements CTReadOnlyNumberProperty<Byte> {
    public CTReadOnlyByteProperty(String name, Byte value) {
        super(name, value);
    }
}
