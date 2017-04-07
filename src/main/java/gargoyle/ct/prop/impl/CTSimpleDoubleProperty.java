package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.impl.DoubleConverter;

public class CTSimpleDoubleProperty extends CTSimpleProperty<Double> {
    public CTSimpleDoubleProperty(String name) {
        this(name, 0);
    }

    public CTSimpleDoubleProperty(String name, double def) {
        super(new DoubleConverter(), name, def);
    }
}
