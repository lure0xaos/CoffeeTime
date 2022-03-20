package gargoyle.ct.pref.impl.prop

import gargoyle.ct.pref.CTPreferencesProvider
import gargoyle.ct.prop.CTNumberProperty

class CTPrefIntegerProperty @JvmOverloads constructor(provider: CTPreferencesProvider, name: String, def: Int = 0) :
    CTPrefProperty<Int>(
        Int::class, provider, name, def
    ), CTNumberProperty<Int>
