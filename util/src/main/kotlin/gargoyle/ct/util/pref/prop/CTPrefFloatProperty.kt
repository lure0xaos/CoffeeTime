package gargoyle.ct.util.pref.prop

import gargoyle.ct.util.pref.CTPreferencesProvider
import gargoyle.ct.util.prop.CTNumberProperty


class CTPrefFloatProperty constructor(provider: CTPreferencesProvider, name: String, def: Float = 0f) :
    CTPrefProperty<Float>(
        Float::class, provider, name, def
    ), CTNumberProperty<Float>
