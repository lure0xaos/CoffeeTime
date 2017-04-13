package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.convert.impl.DoubleConverter;

import java.util.prefs.Preferences;

public class CTPrefDoubleProperty extends CTPrefProperty<Double> {
    public CTPrefDoubleProperty(Preferences preferences, String name) {
        this(preferences, name, 0);
    }

    public CTPrefDoubleProperty(Preferences preferences, String name, double def) {
        super(new DoubleConverter(), preferences, name, def);
    }
}
