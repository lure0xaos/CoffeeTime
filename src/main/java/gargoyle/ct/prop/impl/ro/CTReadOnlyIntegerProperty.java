package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTReadOnlyNumberProperty;

public class CTReadOnlyIntegerProperty extends CTBaseReadOnlyProperty<Integer>
        implements CTReadOnlyNumberProperty<Integer> {
    public CTReadOnlyIntegerProperty(String name, int value) {
        super(name, value);
    }
}
