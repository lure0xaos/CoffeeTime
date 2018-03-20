package gargoyle.ct.prop.impl.simple;

import gargoyle.ct.prop.CTNumberProperty;
import org.jetbrains.annotations.NotNull;

public class CTSimpleShortProperty extends CTSimpleProperty<Short> implements CTNumberProperty<Short> {
    public CTSimpleShortProperty(String name) {
        this(name, (short) 0);
    }

    public CTSimpleShortProperty(String name, @NotNull Short def) {
        super(name, def);
    }
}
