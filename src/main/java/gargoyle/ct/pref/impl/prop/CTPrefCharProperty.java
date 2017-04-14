package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.impl.convert.impl.CharConverter;

import java.util.prefs.Preferences;

public class CTPrefCharProperty extends CTPrefProperty<Character> {

    public CTPrefCharProperty(Preferences preferences, String name) {
        this(preferences, name, '\0');
    }

    public CTPrefCharProperty(Preferences preferences, String name, Character def) {
        super(new CharConverter(), preferences, name, def);
    }
}
