package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTReadOnlyNumberProperty;

public class CTReadOnlyFloatProperty extends CTBaseReadOnlyProperty<Float> implements CTReadOnlyNumberProperty<Float> {
    public CTReadOnlyFloatProperty(String name, float value) {
        super(name, value);
    }
}
