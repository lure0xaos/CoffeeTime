package gargoyle.ct.util.pref.prop

import gargoyle.ct.util.pref.CTPreferencesProvider
import gargoyle.ct.util.prop.CTNumberProperty

class CTPrefIntegerProperty constructor(provider: CTPreferencesProvider, name: String, def: Int = 0) :
    CTPrefProperty<Int>(
        Int::class, provider, name, def
    ), CTNumberProperty<Int>
