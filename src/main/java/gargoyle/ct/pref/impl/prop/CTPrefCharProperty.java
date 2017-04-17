package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.pref.impl.convert.impl.CharConverter;

public class CTPrefCharProperty extends CTPrefProperty<Character> {

    public CTPrefCharProperty(CTPreferencesProvider provider, String name) {
        this(provider, name, '\0');
    }

    public CTPrefCharProperty(CTPreferencesProvider provider, String name, Character def) {
        super(new CharConverter(), provider, name, def);
    }
}
