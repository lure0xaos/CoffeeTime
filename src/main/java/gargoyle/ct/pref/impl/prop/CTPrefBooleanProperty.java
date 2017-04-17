package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.pref.impl.convert.impl.BooleanConverter;

public class CTPrefBooleanProperty extends CTPrefProperty<Boolean> {

    public CTPrefBooleanProperty(CTPreferencesProvider provider, String name) {
        this(provider, name, false);
    }

    public CTPrefBooleanProperty(CTPreferencesProvider provider, String name, boolean def) {
        super(new BooleanConverter(), provider, name, def);
    }
}
