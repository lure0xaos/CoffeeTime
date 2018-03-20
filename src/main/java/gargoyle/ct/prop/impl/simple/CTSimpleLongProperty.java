package gargoyle.ct.prop.impl.simple;

import gargoyle.ct.prop.CTNumberProperty;
import org.jetbrains.annotations.NotNull;

public class CTSimpleLongProperty extends CTSimpleProperty<Long> implements CTNumberProperty<Long> {
    public CTSimpleLongProperty(String name) {
        this(name, 0L);
    }

    public CTSimpleLongProperty(String name, @NotNull Long def) {
        super(name, def);
    }
}
