package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.impl.BooleanConverter;
import gargoyle.ct.pref.CTPreferencesProvider;

public class CTPrefBooleanProperty extends CTPrefProperty<Boolean> {

    public CTPrefBooleanProperty(CTPreferencesProvider provider, String name) {
        this(provider, name, false);
    }

    public CTPrefBooleanProperty(CTPreferencesProvider provider, String name, boolean def) {
        super(Boolean.class, new BooleanConverter(), provider, name, def);
    }
}
