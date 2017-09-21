package gargoyle.ct.util.pref.prop

import gargoyle.ct.util.pref.CTPreferencesProvider
import gargoyle.ct.util.prop.CTNumberProperty


class CTPrefDoubleProperty constructor(
    provider: CTPreferencesProvider,
    name: String,
    def: Double = 0.0
) : CTPrefProperty<Double>(
    Double::class, provider, name, def
), CTNumberProperty<Double>
