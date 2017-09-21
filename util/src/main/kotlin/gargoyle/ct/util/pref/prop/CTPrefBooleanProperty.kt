package gargoyle.ct.util.pref.prop

import gargoyle.ct.util.pref.CTPreferencesProvider


class CTPrefBooleanProperty constructor(
    provider: CTPreferencesProvider,
    name: String,
    def: Boolean = false
) : CTPrefProperty<Boolean>(
    Boolean::class, provider, name, def
)
