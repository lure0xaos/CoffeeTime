package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.prop.CTNumberProperty;

public class CTPrefLongProperty extends CTPrefProperty<Long> implements CTNumberProperty<Long> {

    public CTPrefLongProperty(CTPreferencesProvider provider, String name) {
        this(provider, name, 0L);
    }

    public CTPrefLongProperty(CTPreferencesProvider provider, String name, Long def) {
        super(Long.class, provider, name, def);
    }
}
