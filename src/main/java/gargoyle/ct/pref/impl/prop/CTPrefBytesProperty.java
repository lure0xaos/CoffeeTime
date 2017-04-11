package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.impl.BytesConverter;

import java.util.prefs.Preferences;

public class CTPrefBytesProperty extends CTPrefProperty<byte[]> {
    public CTPrefBytesProperty(Preferences preferences, String name) {
        this(preferences, name, new byte[0]);
    }

    public CTPrefBytesProperty(Preferences preferences, String name, byte[] def) {
        super(new BytesConverter(), preferences, name, def);
    }
}
