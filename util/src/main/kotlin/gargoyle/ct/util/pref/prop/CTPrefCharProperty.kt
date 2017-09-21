package gargoyle.ct.util.pref.prop

import gargoyle.ct.util.pref.CTPreferencesProvider


class CTPrefCharProperty constructor(
    provider: CTPreferencesProvider,
    name: String,
    def: Char = '\u0000'
) : CTPrefProperty<Char>(
    Char::class, provider, name, def
)
