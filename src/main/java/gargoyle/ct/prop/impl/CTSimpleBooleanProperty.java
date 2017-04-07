package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.impl.BooleanConverter;

public class CTSimpleBooleanProperty extends CTSimpleProperty<Boolean> {
    public CTSimpleBooleanProperty(String name) {
        this(name, false);
    }

    public CTSimpleBooleanProperty(String name, boolean def) {
        super(new BooleanConverter(), name, def);
    }
}
