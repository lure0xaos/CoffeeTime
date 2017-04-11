package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.impl.CharConverter;

public class CTSimpleCharProperty extends CTSimpleProperty<Character> {
    public CTSimpleCharProperty(String name) {
        this(name, '\0');
    }

    public CTSimpleCharProperty(String name, Character def) {
        super(new CharConverter(), name, def);
    }
}
