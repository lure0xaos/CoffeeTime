package gargoyle.ct.util.pref.prop

import gargoyle.ct.util.pref.CTPreferencesProvider
import gargoyle.ct.util.prop.CTNumberProperty


class CTPrefByteProperty constructor(
    provider: CTPreferencesProvider,
    name: String,
    def: Byte = 0.toByte()
) : CTPrefProperty<Byte>(
    Byte::class, provider, name, def
), CTNumberProperty<Byte>
