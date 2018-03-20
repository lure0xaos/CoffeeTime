package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTReadOnlyNumberProperty;
import org.jetbrains.annotations.NotNull;

public class CTReadOnlyShortProperty extends CTBaseReadOnlyProperty<Short> implements CTReadOnlyNumberProperty<Short> {
    public CTReadOnlyShortProperty(String name, @NotNull Short value) {
        super(name, value);
    }
}
