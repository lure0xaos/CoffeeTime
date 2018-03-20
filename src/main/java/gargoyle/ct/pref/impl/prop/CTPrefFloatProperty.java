package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.prop.CTNumberProperty;
import org.jetbrains.annotations.NotNull;

public class CTPrefFloatProperty extends CTPrefProperty<Float> implements CTNumberProperty<Float> {
    public CTPrefFloatProperty(@NotNull CTPreferencesProvider provider, String name) {
        this(provider, name, 0);
    }

    public CTPrefFloatProperty(@NotNull CTPreferencesProvider provider, String name, float def) {
        super(Float.class, provider, name, def);
    }
}
