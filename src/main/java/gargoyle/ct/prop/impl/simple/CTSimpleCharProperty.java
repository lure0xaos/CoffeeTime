package gargoyle.ct.prop.impl.simple;

public class CTSimpleCharProperty extends CTSimpleProperty<Character> {

    public CTSimpleCharProperty(String name) {
        this(name, '\0');
    }

    public CTSimpleCharProperty(String name, Character def) {
        super(name, def);
    }
}
