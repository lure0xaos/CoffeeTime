package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.CTPreferencesProvider;
import org.jetbrains.annotations.NotNull;

public class CTPrefBooleanProperty extends CTPrefProperty<Boolean> {
    public CTPrefBooleanProperty(@NotNull CTPreferencesProvider provider, String name) {
        this(provider, name, false);
    }

    public CTPrefBooleanProperty(@NotNull CTPreferencesProvider provider, String name, boolean def) {
        super(Boolean.class, provider, name, def);
    }
}
