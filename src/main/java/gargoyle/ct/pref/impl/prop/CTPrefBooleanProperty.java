package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.CTPreferencesProvider;

public class CTPrefBooleanProperty extends CTPrefProperty<Boolean> {
    public CTPrefBooleanProperty(CTPreferencesProvider provider, String name) {
        this(provider, name, false);
    }

    public CTPrefBooleanProperty(CTPreferencesProvider provider, String name, boolean def) {
        super(Boolean.class, provider, name, def);
    }
}
