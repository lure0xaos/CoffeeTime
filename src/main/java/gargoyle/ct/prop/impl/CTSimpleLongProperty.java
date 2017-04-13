package gargoyle.ct.prop.impl;

public class CTSimpleLongProperty extends CTSimpleProperty<Long> {
    public CTSimpleLongProperty(String name) {
        this(name, 0L);
    }

    public CTSimpleLongProperty(String name, Long def) {
        super(name, def);
    }
}
