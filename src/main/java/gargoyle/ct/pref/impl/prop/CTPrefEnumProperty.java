package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.impl.EnumConverter;
import gargoyle.ct.pref.CTPreferencesProvider;
import org.jetbrains.annotations.NotNull;

public class CTPrefEnumProperty<E extends Enum<E>> extends CTPrefProperty<E> {
    public CTPrefEnumProperty(@NotNull CTPreferencesProvider provider, Class<E> type, String name) {
        super(type, new EnumConverter<>(type), provider, name, null);
    }

    public CTPrefEnumProperty(@NotNull CTPreferencesProvider provider, Class<E> type, String name, E def) {
        super(type, new EnumConverter<>(type), provider, name, def);
    }

    public CTPrefEnumProperty(@NotNull CTPreferencesProvider provider, @NotNull Class<E> type) {
        super(type, new EnumConverter<>(type), provider, type.getSimpleName(), null);
    }

    public CTPrefEnumProperty(@NotNull CTPreferencesProvider provider, @NotNull Class<E> type, E def) {
        super(type, new EnumConverter<>(type), provider, type.getSimpleName(), def);
    }
}
