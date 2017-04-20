package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.impl.FloatConverter;
import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.prop.CTNumberProperty;

public class CTPrefFloatProperty extends CTPrefProperty<Float> implements CTNumberProperty<Float> {

    public CTPrefFloatProperty(CTPreferencesProvider provider, String name) {
        this(provider, name, 0);
    }

    public CTPrefFloatProperty(CTPreferencesProvider provider, String name, float def) {
        super(Float.class, new FloatConverter(), provider, name, def);
    }
}
