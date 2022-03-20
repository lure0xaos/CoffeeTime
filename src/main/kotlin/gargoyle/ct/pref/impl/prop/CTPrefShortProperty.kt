package gargoyle.ct.pref.impl.prop

import gargoyle.ct.pref.CTPreferencesProvider
import gargoyle.ct.prop.CTNumberProperty

class CTPrefShortProperty @JvmOverloads constructor(
    provider: CTPreferencesProvider,
    name: String,
    def: Short = 0.toShort()
) : CTPrefProperty<Short>(
    Short::class, provider, name, def
), CTNumberProperty<Short>
