package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.impl.convert.impl.LongConverter;

import java.util.prefs.Preferences;

public class CTPrefLongProperty extends CTPrefProperty<Long> {

    public CTPrefLongProperty(Preferences preferences, String name) {
        this(preferences, name, 0L);
    }

    public CTPrefLongProperty(Preferences preferences, String name, Long def) {
        super(new LongConverter(), preferences, name, def);
    }
}
