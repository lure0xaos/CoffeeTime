package gargoyle.ct.prop.impl.ro;

import gargoyle.ct.prop.CTRONumberProperty;

public class CTROIntegerProperty extends CTBaseROProperty<Integer> implements CTRONumberProperty<Integer> {

    public CTROIntegerProperty(String name, int value) {
        super(name, value);
    }
}
