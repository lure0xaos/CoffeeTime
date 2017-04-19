package gargoyle.ct.prop.impl.ro;

public class CTROCharProperty extends CTBaseROProperty<Character> {

    @SuppressWarnings("MagicCharacter")
    public CTROCharProperty(String name) {
        this(name, '\0');
    }

    public CTROCharProperty(String name, Character value) {
        super(name, value);
    }
}
