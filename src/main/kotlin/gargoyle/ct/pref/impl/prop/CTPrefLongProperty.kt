package gargoyle.ct.pref.impl.prop

import gargoyle.ct.pref.CTPreferencesProvider
import gargoyle.ct.prop.CTNumberProperty

class CTPrefLongProperty @JvmOverloads constructor(provider: CTPreferencesProvider, name: String, def: Long = 0L) :
    CTPrefProperty<Long>(
        Long::class, provider, name, def
    ), CTNumberProperty<Long>
