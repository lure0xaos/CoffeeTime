package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.CTPreferencesProvider;
import org.jetbrains.annotations.NotNull;

public class CTPrefBytesProperty extends CTPrefProperty<byte[]> {
    public CTPrefBytesProperty(@NotNull CTPreferencesProvider provider, String name) {
        this(provider, name, new byte[0]);
    }

    public CTPrefBytesProperty(@NotNull CTPreferencesProvider provider, String name, byte[] def) {
        super(byte[].class, provider, name, def);
    }
}
