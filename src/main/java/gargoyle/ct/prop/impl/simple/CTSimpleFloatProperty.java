package gargoyle.ct.prop.impl.simple;

import gargoyle.ct.prop.CTNumberProperty;

public class CTSimpleFloatProperty extends CTSimpleProperty<Float> implements CTNumberProperty<Float> {

    public CTSimpleFloatProperty(String name) {
        this(name, 0);
    }

    public CTSimpleFloatProperty(String name, float def) {
        super(name, def);
    }
}
