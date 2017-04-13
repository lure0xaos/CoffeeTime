package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.impl.convert.impl.ShortConverter;

import java.util.prefs.Preferences;

public class CTPrefShortProperty extends CTPrefProperty<Short> {
    public CTPrefShortProperty(Preferences preferences, String name) {
        this(preferences, name, (short) 0);
    }

    public CTPrefShortProperty(Preferences preferences, String name, Short def) {
        super(new ShortConverter(), preferences, name, def);
    }
}
