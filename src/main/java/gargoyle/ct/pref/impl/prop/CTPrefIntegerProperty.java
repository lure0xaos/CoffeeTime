package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.prop.CTNumberProperty;

public class CTPrefIntegerProperty extends CTPrefProperty<Integer> implements CTNumberProperty<Integer> {

    public CTPrefIntegerProperty(CTPreferencesProvider provider, String name) {
        this(provider, name, 0);
    }

    public CTPrefIntegerProperty(CTPreferencesProvider provider, String name, int def) {
        super(Integer.class, provider, name, def);
    }
}
