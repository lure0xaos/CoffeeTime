package gargoyle.ct.prop.impl;

public class CTSimpleCharProperty extends CTSimpleProperty<Character> {
    public CTSimpleCharProperty(String name) {
        this(name, '\0');
    }

    public CTSimpleCharProperty(String name, Character def) {
        super(name, def);
    }
}
