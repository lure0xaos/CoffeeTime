package gargoyle.ct.prop.impl.ro;

public class CTROBooleanProperty extends CTBaseROProperty<Boolean> {

    public CTROBooleanProperty(String name) {
        this(name, false);
    }

    public CTROBooleanProperty(String name, boolean value) {
        super(name, value);
    }
}
