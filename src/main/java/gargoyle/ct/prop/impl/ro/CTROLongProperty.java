package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTRONumberProperty;

public class CTROLongProperty extends CTBaseROProperty<Long> implements CTRONumberProperty<Long> {

    public CTROLongProperty(String name) {
        this(name, 0L);
    }

    public CTROLongProperty(String name, Long value) {
        super(name, value);
    }
}
