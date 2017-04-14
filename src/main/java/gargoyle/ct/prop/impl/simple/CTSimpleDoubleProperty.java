package gargoyle.ct.prop.impl.simple;

public class CTSimpleDoubleProperty extends CTSimpleProperty<Double> {

    public CTSimpleDoubleProperty(String name) {
        this(name, 0);
    }

    public CTSimpleDoubleProperty(String name, double def) {
        super(name, def);
    }
}
