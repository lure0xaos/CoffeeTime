package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.prop.CTNumberProperty;
import org.jetbrains.annotations.NotNull;

public class CTPrefDoubleProperty extends CTPrefProperty<Double> implements CTNumberProperty<Double> {
    public CTPrefDoubleProperty(@NotNull CTPreferencesProvider provider, String name) {
        this(provider, name, 0);
    }

    public CTPrefDoubleProperty(@NotNull CTPreferencesProvider provider, String name, double def) {
        super(Double.class, provider, name, def);
    }
}
