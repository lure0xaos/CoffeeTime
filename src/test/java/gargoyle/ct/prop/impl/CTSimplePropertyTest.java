package gargoyle.ct.prop.impl;

import gargoyle.ct.prop.impl.simple.CTSimpleIntegerProperty;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CTSimplePropertyTest {
    @Test
    public void testBindBi() {
        CTSimpleIntegerProperty prop1 = new CTSimpleIntegerProperty("prop31", 1); //NON-NLS
        CTSimpleIntegerProperty prop2 = new CTSimpleIntegerProperty("prop32", 2); //NON-NLS
        prop1.bindBi(prop2);
        {
            Integer setValue = 3;
            prop1.set(setValue);
            Integer gotValue = prop2.get();
            assertEquals(setValue, gotValue);
        }
        Integer setValue = 4;
        prop2.set(setValue);
        Integer gotValue = prop1.get();
        assertEquals(setValue, gotValue);
    }

    @Test
    public void testBindMutual() {
        CTSimpleIntegerProperty prop1 = new CTSimpleIntegerProperty("prop11", 1); //NON-NLS
        CTSimpleIntegerProperty prop2 = new CTSimpleIntegerProperty("prop12", 2); //NON-NLS
        prop1.bind(prop2);
        prop2.bind(prop1);
        {
            Integer setValue = 3;
            prop1.set(setValue);
            Integer gotValue = prop2.get();
            assertEquals(setValue, gotValue);
        }
        Integer setValue = 4;
        prop2.set(setValue);
        Integer gotValue = prop1.get();
        assertEquals(setValue, gotValue);
    }

    @Test
    public void testBindSimple() {
        CTSimpleIntegerProperty prop1 = new CTSimpleIntegerProperty("prop21", 1); //NON-NLS
        CTSimpleIntegerProperty prop2 = new CTSimpleIntegerProperty("prop22", 2); //NON-NLS
        prop1.bind(prop2);
        Integer setValue = 3;
        prop1.set(setValue);
        Integer gotValue = prop2.get();
        assertEquals(setValue, gotValue);
    }
}
