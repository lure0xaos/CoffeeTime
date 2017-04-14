package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.impl.convert.impl.IntegerConverter;
import gargoyle.ct.prop.CTNumberProperty;

import java.util.prefs.Preferences;

public class CTPrefIntegerProperty extends CTPrefProperty<Integer> implements CTNumberProperty<Integer> {

    public CTPrefIntegerProperty(Preferences preferences, String name) {
        this(preferences, name, 0);
    }

    public CTPrefIntegerProperty(Preferences preferences, String name, int def) {
        super(new IntegerConverter(), preferences, name, def);
    }
}
