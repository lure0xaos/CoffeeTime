package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.impl.ShortConverter;
import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.prop.CTNumberProperty;

public class CTPrefShortProperty extends CTPrefProperty<Short> implements CTNumberProperty<Short> {

    public CTPrefShortProperty(CTPreferencesProvider provider, String name) {
        this(provider, name, (short) 0);
    }

    public CTPrefShortProperty(CTPreferencesProvider provider, String name, Short def) {
        super(new ShortConverter(), provider, name, def);
    }
}
