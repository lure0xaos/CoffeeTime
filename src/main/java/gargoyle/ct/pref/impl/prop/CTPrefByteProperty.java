package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.impl.ByteConverter;
import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.prop.CTNumberProperty;

public class CTPrefByteProperty extends CTPrefProperty<Byte> implements CTNumberProperty<Byte> {

    public CTPrefByteProperty(CTPreferencesProvider provider, String name) {
        this(provider, name, (byte) 0);
    }

    public CTPrefByteProperty(CTPreferencesProvider provider, String name, Byte def) {
        super(Byte.class, new ByteConverter(), provider, name, def);
    }
}
