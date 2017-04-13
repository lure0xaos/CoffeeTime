package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.convert.impl.ByteConverter;

import java.util.prefs.Preferences;

public class CTPrefByteProperty extends CTPrefProperty<Byte> {
    public CTPrefByteProperty(Preferences preferences, String name) {
        this(preferences, name, (byte) 0);
    }

    public CTPrefByteProperty(Preferences preferences, String name, Byte def) {
        super(new ByteConverter(), preferences, name, def);
    }
}
