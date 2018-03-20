package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTReadOnlyNumberProperty;
import org.jetbrains.annotations.NotNull;

public class CTReadOnlyLongProperty extends CTBaseReadOnlyProperty<Long> implements CTReadOnlyNumberProperty<Long> {
    public CTReadOnlyLongProperty(String name, @NotNull Long value) {
        super(name, value);
    }
}
