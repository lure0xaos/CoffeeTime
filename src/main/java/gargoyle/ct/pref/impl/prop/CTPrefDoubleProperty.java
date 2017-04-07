package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.impl.DoubleConverter;

import java.util.prefs.Preferences;

public class CTPrefDoubleProperty extends CTPrefProperty<Double> {
    public CTPrefDoubleProperty(Preferences preferences, String name) {
        super(new DoubleConverter(), preferences, name);
    }
}
