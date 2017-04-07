package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.impl.FloatConverter;

public class CTSimpleFloatProperty extends CTSimpleProperty<Float> {
    public CTSimpleFloatProperty(String name) {
        this(name, 0);
    }

    public CTSimpleFloatProperty(String name, float def) {
        super(new FloatConverter(), name, def);
    }
}
