package gargoyle.ct.pref.impl.prop

import gargoyle.ct.pref.CTPreferencesProvider
import gargoyle.ct.prop.CTNumberProperty

class CTPrefDoubleProperty @JvmOverloads constructor(
    provider: CTPreferencesProvider,
    name: String,
    def: Double = 0.0
) : CTPrefProperty<Double>(
    Double::class, provider, name, def
), CTNumberProperty<Double>
