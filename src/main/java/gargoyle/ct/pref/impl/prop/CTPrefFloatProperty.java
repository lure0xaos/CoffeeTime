package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.impl.convert.impl.FloatConverter;
import gargoyle.ct.prop.CTNumberProperty;

import java.util.prefs.Preferences;

public class CTPrefFloatProperty extends CTPrefProperty<Float> implements CTNumberProperty<Float> {

    public CTPrefFloatProperty(Preferences preferences, String name) {
        this(preferences, name, 0);
    }

    public CTPrefFloatProperty(Preferences preferences, String name, float def) {
        super(new FloatConverter(), preferences, name, def);
    }
}
