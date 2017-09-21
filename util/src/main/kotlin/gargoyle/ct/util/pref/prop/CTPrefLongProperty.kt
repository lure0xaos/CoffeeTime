package gargoyle.ct.util.pref.prop

import gargoyle.ct.util.pref.CTPreferencesProvider
import gargoyle.ct.util.prop.CTNumberProperty


class CTPrefLongProperty constructor(provider: CTPreferencesProvider, name: String, def: Long = 0L) :
    CTPrefProperty<Long>(
        Long::class, provider, name, def
    ), CTNumberProperty<Long>
