package gargoyle.ct.prop.impl.simple;

import org.jetbrains.annotations.NotNull;

public class CTSimpleCharProperty extends CTSimpleProperty<Character> {
    @SuppressWarnings("MagicCharacter")
    public CTSimpleCharProperty(String name) {
        this(name, '\0');
    }

    public CTSimpleCharProperty(String name, @NotNull Character def) {
        super(name, def);
    }
}
