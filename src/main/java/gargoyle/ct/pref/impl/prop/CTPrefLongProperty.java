package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.pref.impl.convert.impl.LongConverter;
import gargoyle.ct.prop.CTNumberProperty;

public class CTPrefLongProperty extends CTPrefProperty<Long> implements CTNumberProperty<Long> {

    public CTPrefLongProperty(CTPreferencesProvider provider, String name) {
        this(provider, name, 0L);
    }

    public CTPrefLongProperty(CTPreferencesProvider provider, String name, Long def) {
        super(new LongConverter(), provider, name, def);
    }
}
