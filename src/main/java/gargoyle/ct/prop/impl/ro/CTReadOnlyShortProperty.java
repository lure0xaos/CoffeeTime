package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTReadOnlyNumberProperty;

public class CTReadOnlyShortProperty extends CTBaseReadOnlyProperty<Short> implements CTReadOnlyNumberProperty<Short> {
    public CTReadOnlyShortProperty(String name, Short value) {
        super(name, value);
    }
}
