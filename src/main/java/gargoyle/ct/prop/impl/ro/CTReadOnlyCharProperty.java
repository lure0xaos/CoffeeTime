package gargoyle.ct.prop.impl.ro;

import org.jetbrains.annotations.NotNull;

public class CTReadOnlyCharProperty extends CTBaseReadOnlyProperty<Character> {
    public CTReadOnlyCharProperty(String name, @NotNull Character value) {
        super(name, value);
    }
}
