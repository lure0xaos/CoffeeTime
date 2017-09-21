package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTReadOnlyNumberProperty;

public class CTReadOnlyLongProperty extends CTBaseReadOnlyProperty<Long> implements CTReadOnlyNumberProperty<Long> {
    public CTReadOnlyLongProperty(String name, Long value) {
        super(name, value);
    }
}
