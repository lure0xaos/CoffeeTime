package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.impl.convert.impl.DoubleConverter;
import gargoyle.ct.prop.CTNumberProperty;

import java.util.prefs.Preferences;

public class CTPrefDoubleProperty extends CTPrefProperty<Double> implements CTNumberProperty<Double> {

    public CTPrefDoubleProperty(Preferences preferences, String name) {
        this(preferences, name, 0);
    }

    public CTPrefDoubleProperty(Preferences preferences, String name, double def) {
        super(new DoubleConverter(), preferences, name, def);
    }
}
