package gargoyle.ct.pref.impl.prop;

import gargoyle.ct.convert.impl.EnumConverter;

import java.util.prefs.Preferences;

public class CTPrefEnumProperty<E extends Enum<E>> extends CTPrefProperty<E> {
    public CTPrefEnumProperty(Preferences preferences, Class<E> type, String name) {
        this(preferences, type, name, null);
    }

    public CTPrefEnumProperty(Preferences preferences, Class<E> type, String name, E def) {
        super(new EnumConverter<>(type), preferences, name, def);
    }

    public CTPrefEnumProperty(Preferences preferences, Class<E> type) {
        this(preferences, type, type.getSimpleName(), null);
    }

    public CTPrefEnumProperty(Preferences preferences, Class<E> type, E def) {
        super(new EnumConverter<>(type), preferences, type.getSimpleName(), def);
    }
}
