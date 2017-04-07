package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.impl.BooleanConverter;

import java.util.prefs.Preferences;

public class CTPrefBooleanProperty extends CTPrefProperty<Boolean> {
    public CTPrefBooleanProperty(Preferences preferences, String name) {
        super(new BooleanConverter(), preferences, name);
    }
}
