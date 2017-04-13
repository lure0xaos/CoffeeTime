package gargoyle.ct.prop.impl;

public class CTSimpleFloatProperty extends CTSimpleProperty<Float> {
    public CTSimpleFloatProperty(String name) {
        this(name, 0);
    }

    public CTSimpleFloatProperty(String name, float def) {
        super(name, def);
    }
}
