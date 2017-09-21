package gargoyle.ct.util.pref.prop

import gargoyle.ct.util.pref.CTPreferencesProvider


class CTPrefBytesProperty constructor(
    provider: CTPreferencesProvider,
    name: String,
    def: ByteArray = ByteArray(0)
) : CTPrefProperty<ByteArray>(
    ByteArray::class, provider, name, def
)
