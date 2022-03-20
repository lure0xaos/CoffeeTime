package gargoyle.ct.pref.impl.prop

import gargoyle.ct.pref.CTPreferencesProvider
import gargoyle.ct.prop.CTNumberProperty

class CTPrefFloatProperty @JvmOverloads constructor(provider: CTPreferencesProvider, name: String, def: Float = 0f) :
    CTPrefProperty<Float>(
        Float::class, provider, name, def
    ), CTNumberProperty<Float>
