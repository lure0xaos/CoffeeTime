package gargoyle.ct.pref.impl.prop

import gargoyle.ct.pref.CTPreferencesProvider

class CTPrefCharProperty @JvmOverloads constructor(
    provider: CTPreferencesProvider,
    name: String,
    def: Char = '\u0000'
) : CTPrefProperty<Char>(
    Char::class, provider, name, def
)
