package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTReadOnlyNumberProperty;

public class CTReadOnlyDoubleProperty extends CTBaseReadOnlyProperty<Double>
        implements CTReadOnlyNumberProperty<Double> {
    public CTReadOnlyDoubleProperty(String name, double value) {
        super(name, value);
    }
}
