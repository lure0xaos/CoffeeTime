package gargoyle.ct.prop.impl;

import gargoyle.ct.convert.impl.LongConverter;

public class CTSimpleLongProperty extends CTSimpleProperty<Long> {
    public CTSimpleLongProperty(String name) {
        this(name, 0L);
    }

    public CTSimpleLongProperty(String name, Long def) {
        super(new LongConverter(), name, def);
    }
}
