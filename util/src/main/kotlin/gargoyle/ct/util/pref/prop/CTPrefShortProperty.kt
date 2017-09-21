package gargoyle.ct.util.pref.prop

import gargoyle.ct.util.pref.CTPreferencesProvider
import gargoyle.ct.util.prop.CTNumberProperty


class CTPrefShortProperty constructor(
    provider: CTPreferencesProvider,
    name: String,
    def: Short = 0.toShort()
) : CTPrefProperty<Short>(
    Short::class, provider, name, def
), CTNumberProperty<Short>
