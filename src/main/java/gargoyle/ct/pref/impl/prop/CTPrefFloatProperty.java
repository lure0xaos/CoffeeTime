package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.impl.FloatConverter;

import java.util.prefs.Preferences;

public class CTPrefFloatProperty extends CTPrefProperty<Float> {
    public CTPrefFloatProperty(Preferences preferences, String name) {
        this(preferences, name, 0);
    }

    public CTPrefFloatProperty(Preferences preferences, String name, float def) {
        super(new FloatConverter(), preferences, name, def);
    }
}
