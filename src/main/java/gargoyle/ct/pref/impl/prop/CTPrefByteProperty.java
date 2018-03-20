package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.prop.CTNumberProperty;
import org.jetbrains.annotations.NotNull;

public class CTPrefByteProperty extends CTPrefProperty<Byte> implements CTNumberProperty<Byte> {
    public CTPrefByteProperty(@NotNull CTPreferencesProvider provider, String name) {
        this(provider, name, (byte) 0);
    }

    public CTPrefByteProperty(@NotNull CTPreferencesProvider provider, String name, Byte def) {
        super(Byte.class, provider, name, def);
    }
}
