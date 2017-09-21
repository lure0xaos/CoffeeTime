package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.CTPreferencesProvider;

public class CTPrefBytesProperty extends CTPrefProperty<byte[]> {
    public CTPrefBytesProperty(CTPreferencesProvider provider, String name) {
        this(provider, name, new byte[0]);
    }

    public CTPrefBytesProperty(CTPreferencesProvider provider, String name, byte[] def) {
        super(byte[].class, provider, name, def);
    }
}
