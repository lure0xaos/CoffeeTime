package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.pref.CTPreferencesProvider;
import org.jetbrains.annotations.NotNull;

public class CTPrefCharProperty extends CTPrefProperty<Character> {
    @SuppressWarnings("MagicCharacter")
    public CTPrefCharProperty(@NotNull CTPreferencesProvider provider, String name) {
        this(provider, name, '\0');
    }

    public CTPrefCharProperty(@NotNull CTPreferencesProvider provider, String name, Character def) {
        super(Character.class, provider, name, def);
    }
}
