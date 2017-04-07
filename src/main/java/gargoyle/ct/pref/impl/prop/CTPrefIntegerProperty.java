package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.impl.IntegerConverter;

import java.util.prefs.Preferences;

public class CTPrefIntegerProperty extends CTPrefProperty<Integer> {
    public CTPrefIntegerProperty(Preferences preferences, String name) {
        super(new IntegerConverter(), preferences, name);
    }
}
