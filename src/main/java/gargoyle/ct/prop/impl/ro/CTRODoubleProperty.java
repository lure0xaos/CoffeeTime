package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTRONumberProperty;

public class CTRODoubleProperty extends CTBaseROProperty<Double> implements CTRONumberProperty<Double> {

    public CTRODoubleProperty(String name) {
        this(name, 0);
    }

    public CTRODoubleProperty(String name, double value) {
        super(name, value);
    }
}
