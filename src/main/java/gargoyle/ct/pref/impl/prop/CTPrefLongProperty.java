package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.CTPreferencesProvider;
import gargoyle.ct.prop.CTNumberProperty;
import org.jetbrains.annotations.NotNull;

public class CTPrefLongProperty extends CTPrefProperty<Long> implements CTNumberProperty<Long> {
    public CTPrefLongProperty(@NotNull CTPreferencesProvider provider, String name) {
        this(provider, name, 0L);
    }

    public CTPrefLongProperty(@NotNull CTPreferencesProvider provider, String name, Long def) {
        super(Long.class, provider, name, def);
    }
}
