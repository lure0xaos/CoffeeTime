package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.impl.convert.impl.BooleanConverter;

import java.util.prefs.Preferences;

public class CTPrefBooleanProperty extends CTPrefProperty<Boolean> {
    public CTPrefBooleanProperty(Preferences preferences, String name) {
        this(preferences, name, false);
    }

    public CTPrefBooleanProperty(Preferences preferences, String name, boolean def) {
        super(new BooleanConverter(), preferences, name, def);
    }
}
